package mi.board.domain.board.comment.controller;

import lombok.RequiredArgsConstructor;
import mi.board.domain.board.comment.dto.CommentRequestDto;
import mi.board.domain.board.comment.dto.CommentResponseDto;
import mi.board.domain.board.comment.entity.Comment;
import mi.board.domain.board.comment.service.CommentService;
import mi.board.domain.board.entity.Board;
import mi.board.domain.board.service.BoardService;
import mi.board.global.dto.ApiResponse;
import mi.board.global.security.userdetails.UserDetailsImpl;
import mi.board.global.utils.ResponseUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static mi.board.global.enums.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    // 댓글 생성
    @PostMapping("/{boardId}")
    public ApiResponse<?> createComment(
            @PathVariable Long boardId,
            @RequestBody @Validated CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Board board = boardService.getBoardById(boardId);
        Comment comment = commentService.createComment(commentRequestDto, userDetails, board);
        return ResponseUtils.success(COMMENT_CREATE_SUCCESS, new CommentResponseDto(comment));
    }

    // 특정 게시글의 댓글 목록 조회
    @GetMapping("/{boardId}")
    public ApiResponse<?> getComments(@PathVariable Long boardId) {
        Board board = boardService.getBoardById(boardId);
        List<CommentResponseDto> comments = commentService.getCommentList(board).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        return ResponseUtils.success(COMMENT_GET_SUCCESS, comments);
    }

    // 댓글 조회
    @GetMapping("/detail/{commentId}")
    public ApiResponse<?> getComment(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return ResponseUtils.success(COMMENT_GET_SUCCESS, new CommentResponseDto(comment));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ApiResponse<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Validated CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Comment updatedComment = commentService.updateComment(commentId, commentRequestDto, userDetails);
        return ResponseUtils.success(COMMENT_UPDATE_SUCCESS, new CommentResponseDto(updatedComment));
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ApiResponse<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.deleteCommentById(commentId, userDetails);
        return ResponseUtils.success(COMMENT_DELETE_SUCCESS, null);
    }
}
