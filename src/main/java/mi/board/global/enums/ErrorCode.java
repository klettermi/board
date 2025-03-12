package mi.board.global.enums;

import lombok.Generated;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    JWT_PREFIX_ERROR(HttpStatus.BAD_REQUEST, "인증 식별자 형식이 잘못되었습니다."),
    USER_LOGIN_FAILURE(HttpStatus.BAD_REQUEST, "로그인 실패하였습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN,  "게시물 수정 또는 삭제 권한이 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
    JWT_EXPIRATION(HttpStatus.UNAUTHORIZED, "유효시간 만료, 재인증이 필요합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시판 정보를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    private ErrorCode(final HttpStatus httpStatus, final String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }

    @Generated
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Generated
    public String getDetail() {
        return this.detail;
    }
}