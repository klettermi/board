package mi.board.global.enums;

import lombok.Generated;
import org.springframework.http.HttpStatus;

public enum SuccessCode {
    // USER
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공했습니다."),

    // BOARD
    BOARD_CREATE_SUCCESS(HttpStatus.CREATED, "게시글 작성을 성공하였습니다."),
    BOARD_GET_SUCCESS(HttpStatus.OK, "게시글 조회를 성공했습니다."),
    BOARD_UPDATE_SUCCESS(HttpStatus.OK, "게시글 수정을 성공했습니다."),
    BOARD_DELETE_SUCCESS(HttpStatus.OK, "게시글 삭제를 성공했습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String detail;

    private SuccessCode(HttpStatus httpStatus, String detail) {
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
