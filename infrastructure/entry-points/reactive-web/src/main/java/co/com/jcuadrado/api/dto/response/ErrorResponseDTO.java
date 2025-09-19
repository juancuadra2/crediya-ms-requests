package co.com.jcuadrado.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;

import static co.com.jcuadrado.api.constant.doc.ErrorResponseDtoConstants.*;

@Builder(toBuilder = true)
@Schema(description = ERROR_RESPONSE_DESCRIPTION)
public record ErrorResponseDTO(
        @Schema(description = MESSAGES_DESCRIPTION, example = MESSAGES_EXAMPLE)
        Set<String> messages
) {
}
