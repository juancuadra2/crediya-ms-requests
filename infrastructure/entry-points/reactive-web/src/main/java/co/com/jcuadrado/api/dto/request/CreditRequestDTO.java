package co.com.jcuadrado.api.dto.request;

import co.com.jcuadrado.api.constant.doc.CreditRequestDtoConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = CreditRequestDtoConstants.CREDIT_REQUEST_RESPONSE_DESCRIPTION)
public record CreditRequestDTO(
        
        @Schema(description = CreditRequestDtoConstants.AMOUNT_DESCRIPTION,
                example = CreditRequestDtoConstants.AMOUNT_EXAMPLE)
        BigDecimal amount,
        
        @Schema(description = CreditRequestDtoConstants.LIMIT_DATE_DESCRIPTION,
                example = CreditRequestDtoConstants.LIMIT_DATE_EXAMPLE)
        LocalDate limitDate,
        
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
