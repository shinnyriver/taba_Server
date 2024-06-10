package taba.tabaServer.tabaserver.dto.global;

import lombok.Getter;
import taba.tabaServer.tabaserver.exception.ErrorCode;

@Getter
public class ExceptionDto {
    private final Integer code;
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
