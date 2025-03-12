package mi.board.domain.board.service;

import mi.board.domain.board.dto.BoardRequestDto;
import mi.board.domain.board.entity.Board;
import mi.board.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    void createBoard() {
        // given
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("제목")
                .contents("내용")
                .build();
        Board board = boardRequestDto.toEntity();
        when(boardRepository.save(any(Board.class))).thenReturn(board);

        // when
        Board savedBoard = boardService.createBoard(boardRequestDto);

        // then
        assertNotNull(savedBoard);
        assertEquals("제목", savedBoard.getTitle());
        assertEquals("내용", savedBoard.getContents());
    }

    @Test
    void getBoardList() {
        // given
        Board board1 = Board.builder()
                .title("제목1")
                .contents("내용1")
                .build();
        Board board2 = Board.builder()
                .title("제목2")
                .contents("내용2")
                .build();
        List<Board> boardListSample = List.of(board1, board2);

        when(boardRepository.findAll()).thenReturn(boardListSample);

        // when
        List<Board> boardList = boardService.getBoardList();

        // then
        assertNotNull(boardList);
        assertEquals(2, boardList.size());
        assertEquals("제목1", boardList.get(0).getTitle());
        assertEquals("내용1", boardList.get(0).getContents());
        assertEquals("제목2", boardList.get(1).getTitle());
        assertEquals("내용2", boardList.get(1).getContents());
    }

    @Test
    void getBoardById() {
        // given
        Long boardId = 1L;
        Board boardSample = Board.builder()
                .id(boardId)
                .title("제목")
                .contents("내용")
                .build();
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(boardSample));

        // when
        Board foundBoard = boardService.getBoardById(boardId);

        // then
        assertNotNull(foundBoard);
        assertEquals(boardId, foundBoard.getId());
        assertEquals("제목", foundBoard.getTitle());
        assertEquals("내용", foundBoard.getContents());
    }

    @Test
    void getBoardByIdNotFound() {
        // given
        Long boardId = 999L;
        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class, () -> boardService.getBoardById(boardId));
    }

    @Test
    void updateBoard() {
        // given
        Long boardId = 1L;
        BoardRequestDto newBoardRequestDto = BoardRequestDto.builder()
                .title("새로운 제목")
                .contents("새로운 내용")
                .build();

        Board existingBoard = Board.builder()
                .title("제목")
                .contents("내용")
                .build();
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(existingBoard));
        when(boardRepository.save(any(Board.class))).thenReturn(existingBoard);

        // when
        Board updateBoard = boardService.updateBoard(boardId, newBoardRequestDto);

        // then
        assertNotNull(updateBoard);
        assertEquals("새로운 제목", updateBoard.getTitle());
        assertEquals("새로운 내용", updateBoard.getContents());
    }

    @Test
    void deleteBoard() {
        // given
        Long boardId = 1L;
        Board existingBoard = Board.builder()
                .title("제목")
                .contents("내용")
                .build();

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(existingBoard));
        doNothing().when(boardRepository).deleteById(boardId);

        // when
        boardService.deleteBoardById(boardId);

        // then
        verify(boardRepository, times(1)).deleteById(boardId); //정확히 한 번 호출되었는지 검증
    }

}