package mi.board.domain.board.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mi.board.domain.board.comment.dto.CommentRequestDto;
import mi.board.domain.board.entity.Board;
import mi.board.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Comment(CommentRequestDto commentRequestDto, Board board, User user) {
        this.content = commentRequestDto.getContent();
        this.board = board;
        this.user = user;
    }

    public void update(String content) {
        this.content = content;
    }
}
