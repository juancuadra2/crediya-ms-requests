package co.com.jcuadrado.api.constant.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HttpStatusConstants {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    NO_CONTENT(HttpStatus.NO_CONTENT),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus httpStatus;

    HttpStatusConstants(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
