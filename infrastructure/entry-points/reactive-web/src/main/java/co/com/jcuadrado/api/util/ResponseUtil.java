package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.api.SuccessHttpStatus;
import co.com.jcuadrado.api.dto.response.ErrorResponseDTO;
import lombok.NoArgsConstructor;
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

    public static ErrorResponseDTO buildErrorResponse(Set<String> messages) {
        return ErrorResponseDTO.builder()
                .messages(messages)
                .build();
    }

}
