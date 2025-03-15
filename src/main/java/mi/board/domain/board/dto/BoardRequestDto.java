package mi.board.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import mi.board.domain.board.entity.Board;
import mi.board.domain.user.entity.User;

@Getter
@Builder
public class BoardRequestDto {
    private String title;
    private String contents;
}
