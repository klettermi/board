package mi.board.global.security.exception;

import mi.board.global.enums.ErrorCode;
import mi.board.global.exception.GlobalException;

public class JwtPrefixException extends GlobalException {
	public JwtPrefixException(ErrorCode errorCode) {
		super(errorCode);
	}
}
