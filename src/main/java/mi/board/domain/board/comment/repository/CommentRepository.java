package mi.board.domain.board.comment.repository;

import mi.board.domain.board.comment.entity.Comment;
import mi.board.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard(Board board);
}
