package mi.board.domain.board.service;

import lombok.RequiredArgsConstructor;
import mi.board.domain.board.dto.BoardRequestDto;
import mi.board.domain.board.entity.Board;
import mi.board.domain.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(BoardRequestDto boardRequestDto) {
        Board board = boardRequestDto.toEntity();
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NoSuchElementException("찾을 수 없는 게시물입니다.")
        );
    }

    @Transactional
    public Board updateBoard(Long boardId, BoardRequestDto newBoardRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NoSuchElementException("찾을 수 없는 게시물입니다.")
        );
        board.update(newBoardRequestDto);
        return boardRepository.save(board);
    }

    public void deleteBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NoSuchElementException("찾을 수 없는 게시물입니다.")
        );
        boardRepository.deleteById(boardId);
    }
}
