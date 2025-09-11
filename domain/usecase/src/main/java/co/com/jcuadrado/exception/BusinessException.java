package co.com.jcuadrado.exception;

import co.com.jcuadrado.constant.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    public final ErrorCode code;

    public BusinessException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
}