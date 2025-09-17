package co.com.jcuadrado.api.dto.response;

import co.com.jcuadrado.api.constant.doc.CreditRequestDtoConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = CreditRequestDtoConstants.CREDIT_REQUEST_RESPONSE_DESCRIPTION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreditRequestDTO(
        
        @Schema(description = CreditRequestDtoConstants.AMOUNT_DESCRIPTION,
                example = CreditRequestDtoConstants.AMOUNT_EXAMPLE)
        BigDecimal amount,
        
        @Schema(description = CreditRequestDtoConstants.TERM_DESCRIPTION,
                example = CreditRequestDtoConstants.TERM_EXAMPLE)
        Integer term,
        
        @Schema(description = CreditRequestDtoConstants.DOCUMENT_NUMBER_DESCRIPTION,
                example = CreditRequestDtoConstants.DOCUMENT_NUMBER_EXAMPLE)
        String documentNumber,
        
        @Schema(description = CreditRequestDtoConstants.TYPE_DESCRIPTION,
                example = CreditRequestDtoConstants.TYPE_EXAMPLE)
        String type,
        
        @Schema(description = CreditRequestDtoConstants.STATUS_DESCRIPTION,
                example = CreditRequestDtoConstants.STATUS_EXAMPLE)
        String status
) {
}
