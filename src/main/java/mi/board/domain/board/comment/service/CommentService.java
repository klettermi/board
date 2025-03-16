package mi.board.domain.board.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mi.board.domain.board.comment.dto.CommentRequestDto;
import mi.board.domain.board.comment.entity.Comment;
import mi.board.domain.board.comment.exception.CommentNotFoundException;
import mi.board.domain.board.comment.repository.CommentRepository;
import mi.board.domain.board.entity.Board;
import mi.board.domain.board.exception.UnauthorizedAccessException;
import mi.board.domain.user.entity.User;
import mi.board.global.security.userdetails.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 생성
    @Transactional
    public Comment createComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails, Board board) {
        User user = userDetails.getUser();
        Comment comment = new Comment(commentRequestDto, board, user);
        return commentRepository.save(comment);
    }

    // 게시글에 달린 댓글 목록 조회
    public List<Comment> getCommentList(Board board) {
        return commentRepository.findByBoard(board);
    }

    // 댓글 ID로 조회
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }

    // 댓글 수정
    @Transactional
    public Comment updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        // 댓글 수정 권한 검사: 작성자만 수정 가능
        if (!comment.getUser().equals(userDetails.getUser())) {
            throw new UnauthorizedAccessException();
        }

        comment.update(commentRequestDto.getContent());
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteCommentById(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        // 댓글 삭제 권한 검사: 작성자만 삭제 가능
        if (!comment.getUser().equals(userDetails.getUser())) {
            throw new UnauthorizedAccessException();
        }

        commentRepository.delete(comment);
    }

}
