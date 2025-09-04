package co.com.jcuadrado.consumer.exceptions;

import lombok.Getter;

@Getter
public class RestConsumerException extends RuntimeException {

    public enum ErrorType {
        NOT_FOUND,
        CONFLICT,
        FORBIDDEN,
        BAD_REQUEST,
        UNPROCESSABLE
    }

    private final ErrorType type;

    public RestConsumerException(ErrorType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

}
