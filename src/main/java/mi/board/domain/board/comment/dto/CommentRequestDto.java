package mi.board.domain.board.comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String content;
}
