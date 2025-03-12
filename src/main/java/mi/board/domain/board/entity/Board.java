package mi.board.domain.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mi.board.domain.board.dto.BoardRequestDto;
import mi.board.domain.user.entity.User;

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

    @ManyToOne
    private User user;

    public void update(BoardRequestDto newBoardRequestDto) {
        this.title = newBoardRequestDto.getTitle();
        this.contents = newBoardRequestDto.getContents();
    }
}
