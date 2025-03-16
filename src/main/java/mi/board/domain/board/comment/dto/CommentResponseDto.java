package mi.board.domain.board.comment.dto;

import lombok.Getter;
import mi.board.domain.board.comment.entity.Comment;


@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String username;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
    }
}
