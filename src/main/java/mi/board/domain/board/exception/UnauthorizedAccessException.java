package mi.board.domain.board.exception;

import mi.board.global.enums.ErrorCode;
import mi.board.global.exception.GlobalException;

public class UnauthorizedAccessException extends GlobalException {
    public UnauthorizedAccessException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS);
    }
}