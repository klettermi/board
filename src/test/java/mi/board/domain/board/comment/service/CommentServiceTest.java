package mi.board.domain.board.comment.service;

import mi.board.domain.board.comment.dto.CommentRequestDto;
import mi.board.domain.board.comment.entity.Comment;
import mi.board.domain.board.comment.exception.CommentNotFoundException;
import mi.board.domain.board.comment.repository.CommentRepository;
import mi.board.domain.board.dto.BoardRequestDto;
import mi.board.domain.board.entity.Board;
import mi.board.domain.board.exception.UnauthorizedAccessException;
import mi.board.domain.user.entity.Role;
import mi.board.domain.user.entity.User;
import mi.board.global.security.userdetails.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void createComment() {
        // given
        User user = User.builder()
                .username("사용자명")
                .password("123456")
                .role(Role.USER)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("제목")
                .contents("내용")
                .build();
        Board board = new Board(boardRequestDto, user);
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("댓글 내용")
                .build();
        Comment comment = new Comment(commentRequestDto, board, user);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // when
        Comment savedComment = commentService.createComment(commentRequestDto, userDetails, board);

        // then
        assertNotNull(savedComment);
        assertEquals("댓글 내용", savedComment.getContent());
        assertEquals(board, savedComment.getBoard());
        assertEquals(user, savedComment.getUser());
    }

    @Test
    void getCommentList() {
        // given
        User user = User.builder()
                .username("사용자명")
                .password("123456")
                .role(Role.USER)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("제목")
                .contents("내용")
                .build();
        Board board = new Board(boardRequestDto, user);
        CommentRequestDto commentRequestDto1 = CommentRequestDto.builder()
                .content("댓글 내용1")
                .build();
        Comment comment1 = new Comment(commentRequestDto1, board, user);

        CommentRequestDto commentRequestDto2 = CommentRequestDto.builder()
                .content("댓글 내용2")
                .build();
        Comment comment2 = new Comment(commentRequestDto2, board, user);

        List<Comment> commentListSample = List.of(comment1, comment2);

        when(commentRepository.findByBoard(board)).thenReturn(commentListSample);

        // when
        List<Comment> commentList = commentService.getCommentList(board);

        // then
        assertNotNull(commentList);
        assertEquals(2, commentList.size());
        assertEquals("댓글 내용1", commentList.get(0).getContent());
        assertEquals("댓글 내용2", commentList.get(1).getContent());
    }

    @Test
    void getCommentById() {
        // given
        User user = User.builder()
                .username("사용자명")
                .password("123456")
                .role(Role.USER)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("제목")
                .contents("내용")
                .build();
        Board board = new Board(boardRequestDto, user);
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("댓글 내용")
                .build();
        Comment comment = new Comment(commentRequestDto, board, user);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        // when
        Comment foundComment = commentService.getCommentById(comment.getId());

        // then
        assertNotNull(foundComment);
        assertEquals(comment.getId(), foundComment.getId());
        assertEquals("댓글 내용", foundComment.getContent());
    }

    @Test
    void getCommentByIdNotFound() {
        // given
        Long commentId = 999L;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CommentNotFoundException.class, () -> commentService.getCommentById(commentId));
    }

    @Test
    void updateComment() {
        // given
        User user = User.builder()
                .username("사용자명")
                .password("123456")
                .role(Role.USER)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("제목")
                .contents("내용")
                .build();
        Board board = new Board(boardRequestDto, user);
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("기존 댓글 내용")
                .build();
        CommentRequestDto newCommentRequestDto = CommentRequestDto.builder()
                .content("새로운 댓글 내용")
                .build();

        Comment existingComment = new Comment(commentRequestDto, board, user);

        when(commentRepository.findById(existingComment.getId())).thenReturn(Optional.of(existingComment));
        // Mock the behavior of update() if necessary, depending on your implementation

        // when
        Comment updatedComment = commentService.updateComment(existingComment.getId(), newCommentRequestDto, userDetails);

        // then
        assertNotNull(updatedComment);
        assertEquals("새로운 댓글 내용", updatedComment.getContent());
    }


    @Test
    void updateCommentNotAccess() {
        // given
        User user = User.builder()
                .username("사용자명")
                .password("123456")
                .role(Role.USER)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        User boardUser = User.builder()
                .username("다른 사용자명")
                .password("123456")
                .role(Role.USER)
                .build();

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("제목")
                .contents("내용")
                .build();
        Board board = new Board(boardRequestDto, user);
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("기존 댓글 내용")
                .build();
        CommentRequestDto newCommentRequestDto = CommentRequestDto.builder()
                .content("새로운 댓글 내용")
                .build();
        Comment comment = new Comment(newCommentRequestDto, board, boardUser);

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        // when & then
        assertThrows(UnauthorizedAccessException.class, () -> commentService.updateComment(comment.getId(), newCommentRequestDto, userDetails));
    }

    @Test
    void deleteComment() {
        // given
        User user = User.builder()
                .username("사용자명")
                .password("123456")
                .role(Role.USER)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("제목")
                .contents("내용")
                .build();
        Board board = new Board(boardRequestDto, user);
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("새로운 댓글 내용")
                .build();

        Comment existingComment = new Comment(commentRequestDto, board, user);
        Long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        doNothing().when(commentRepository).delete(any(Comment.class)); // 기존 deleteById 대신 delete 사용

        // when
        commentService.deleteCommentById(commentId, userDetails);

        // then
        verify(commentRepository, times(1)).findById(commentId); // findById가 한 번 호출되었는지 확인
        verify(commentRepository, times(1)).delete(any(Comment.class)); // deleteById -> delete 변경
    }


    @Test
    void deleteCommentNotAccess() {
        // given
        User user = User.builder()
                .username("사용자명")
                .password("123456")
                .role(Role.USER)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        User commentUser = User.builder()
                .username("다른 사용자명")
                .password("123456")
                .role(Role.USER)
                .build();

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("제목")
                .contents("내용")
                .build();
        Board board = new Board(boardRequestDto, user);
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("댓글 내용")
                .build();

        Comment existingComment = new Comment(commentRequestDto, board, commentUser);
        Long commentId = existingComment.getId();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));

        // when & then
        assertThrows(UnauthorizedAccessException.class, () -> commentService.deleteCommentById(commentId, userDetails));
    }
}