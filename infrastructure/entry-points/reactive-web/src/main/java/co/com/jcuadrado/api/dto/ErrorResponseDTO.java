package co.com.jcuadrado.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;

import static co.com.jcuadrado.api.constant.doc.ErrorResponseDtoConstants.*;

@Builder(toBuilder = true)
@Schema(description = ERROR_RESPONSE_DESCRIPTION)
public record ErrorResponseDTO(
        @Schema(description = MESSAGES_DESCRIPTION, example = MESSAGES_EXAMPLE)
        Set<String> messages,
        
        @Schema(description = ERROR_DESCRIPTION, example = ERROR_EXAMPLE)
        String error,
        
        @Schema(description = STATUS_DESCRIPTION, example = STATUS_EXAMPLE)
        String status
) {
}
