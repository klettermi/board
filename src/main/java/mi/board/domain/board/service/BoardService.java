package mi.board.domain.board.service;

import lombok.RequiredArgsConstructor;
import mi.board.domain.board.dto.BoardRequestDto;
import mi.board.domain.board.entity.Board;
import mi.board.domain.board.exception.BoardNotFoundException;
import mi.board.domain.board.exception.UnauthorizedAccessException;
import mi.board.domain.board.repository.BoardRepository;
import mi.board.domain.user.entity.User;
import mi.board.global.security.userdetails.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(BoardRequestDto boardRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Board board = boardRequestDto.toEntity(user);
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                BoardNotFoundException::new);

    }

    @Transactional
    public Board updateBoard(Long boardId, BoardRequestDto newBoardRequestDto, UserDetailsImpl userDetails) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                BoardNotFoundException::new);

        validateUserAuthorization(userDetails.getUser(), board);

        board.update(newBoardRequestDto);
        return board;
    }

    @Transactional
    public void deleteBoardById(Long boardId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Board board = boardRepository.findById(boardId).orElseThrow(
                BoardNotFoundException::new);

        validateUserAuthorization(user, board);

        boardRepository.deleteById(boardId);
    }

    private void validateUserAuthorization(User user, Board board) {
        if (!user.getUsername().equals(board.getUser().getUsername()) && !user.isAdmin()) {
            throw new UnauthorizedAccessException();
        }
    }
}
