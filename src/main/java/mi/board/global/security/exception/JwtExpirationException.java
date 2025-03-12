package mi.board.global.security.exception;


import mi.board.global.enums.ErrorCode;
import mi.board.global.exception.GlobalException;

public class JwtExpirationException extends GlobalException {
	public JwtExpirationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
