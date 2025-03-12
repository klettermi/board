package mi.board.domain.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mi.board.domain.board.dto.BoardRequestDto;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    public void update(BoardRequestDto newBoardRequestDto) {
        this.title = newBoardRequestDto.getTitle();
        this.contents = newBoardRequestDto.getContents();
    }
}
