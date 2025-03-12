package mi.board.domain.board.controller;

import lombok.RequiredArgsConstructor;
import mi.board.domain.board.service.BoardService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
}
