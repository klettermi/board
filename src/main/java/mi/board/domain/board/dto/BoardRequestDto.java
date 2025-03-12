package mi.board.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import mi.board.domain.board.entity.Board;

@Getter
@Builder
public class BoardRequestDto {
    private String title;
    private String contents;

    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .contents(this.contents)
                .build();
    }
}
