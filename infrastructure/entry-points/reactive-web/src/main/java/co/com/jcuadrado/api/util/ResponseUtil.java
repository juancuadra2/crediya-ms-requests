package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.api.SuccessHttpStatus;
import co.com.jcuadrado.api.dto.ErrorResponseDTO;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ResponseUtil {

    private static final MediaType DEFAULT_CONTENT_TYPE = MediaType.APPLICATION_JSON;

    public static <T> Mono<ServerResponse> buildSuccessResponse(T dto, SuccessHttpStatus status) {
        return ServerResponse
                .status(status.getHttpStatus())
                .contentType(DEFAULT_CONTENT_TYPE)
                .bodyValue(dto);
    }

    public static ErrorResponseDTO buildErrorResponse(Set<String> messages, HttpStatus status) {
        return ErrorResponseDTO.builder()
                .messages(messages)
                .error(status.name())
                .status(String.valueOf(status.value()))
                .build();
    }

}
