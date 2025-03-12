package mi.board.domain.board.controller;

import lombok.RequiredArgsConstructor;
import mi.board.domain.board.dto.BoardRequestDto;
import mi.board.domain.board.entity.Board;
import mi.board.domain.board.service.BoardService;
import mi.board.global.dto.ApiResponse;
import mi.board.global.security.userdetails.UserDetailsImpl;
import mi.board.global.utils.ResponseUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mi.board.global.enums.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ApiResponse<?> createBoard(@RequestBody @Validated BoardRequestDto boardRequestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.success(BOARD_CREATE_SUCCESS, boardService.createBoard(boardRequestDto, userDetails));
    }

    @GetMapping
    public ApiResponse<?> getBoardList() {
        List<Board> boardList = boardService.getBoardList();
        return ResponseUtils.success(BOARD_GET_SUCCESS, boardList);
    }

    @GetMapping("/{boardId}")
    public ApiResponse<?> getBoardById(@PathVariable Long boardId) {
        Board board = boardService.getBoardById(boardId);
        return ResponseUtils.success(BOARD_GET_SUCCESS, board);
    }

    @PutMapping("/{boardId}")
    public ApiResponse<?> updateBoard(@PathVariable Long boardId,
                                      @RequestBody @Validated BoardRequestDto newBoardRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Board updatedBoard = boardService.updateBoard(boardId, newBoardRequestDto, userDetails);
        return ResponseUtils.success(BOARD_UPDATE_SUCCESS, updatedBoard);
    }

    @DeleteMapping("/{boardId}")
    public ApiResponse<?> deleteBoard(@PathVariable Long boardId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoardById(boardId, userDetails);
        return ResponseUtils.success(BOARD_DELETE_SUCCESS, null);
    }
}
