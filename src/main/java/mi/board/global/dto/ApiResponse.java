package mi.board.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;

@Schema(description = "공통 응답 폼")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @Schema(description = "status")
    private int status;

    @Schema(description = "message")
    private String message;

    @Schema(description = "data")
    private T data;

    @Generated
    public int getStatus() {
        return this.status;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }

    @Generated
    public T getData() {
        return this.data;
    }

    @Generated
    public ApiResponse(final int status, final String message, final T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
