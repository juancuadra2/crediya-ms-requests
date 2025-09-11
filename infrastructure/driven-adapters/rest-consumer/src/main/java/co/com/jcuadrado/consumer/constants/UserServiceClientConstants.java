package co.com.jcuadrado.consumer.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserServiceClientConstants {
    public static final String USER_SERVICE_CIRCUIT_BREAKER_NAME = "getUserByDocumentNumber";
    public static final String USER_BY_DOCUMENT_NUMBER_URI = "/api/users/{documentNumber}";
    public static final String BAD_REQUEST_ERROR_MESSAGE = "Bad Request";
    public static final String CONNECTION_ERROR_MESSAGE = "Connection error with user service";
}
