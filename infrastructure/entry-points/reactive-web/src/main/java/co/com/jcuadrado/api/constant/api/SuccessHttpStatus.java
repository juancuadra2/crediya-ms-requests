package co.com.jcuadrado.api.constant.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessHttpStatus {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED);

    private final HttpStatus httpStatus;

    SuccessHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
