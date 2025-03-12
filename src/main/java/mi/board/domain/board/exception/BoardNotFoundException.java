package mi.board.domain.board.exception;

import mi.board.global.enums.ErrorCode;
import mi.board.global.exception.GlobalException;

public class BoardNotFoundException extends GlobalException {
    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
