package co.com.jcuadrado.api.dto.response;

import co.com.jcuadrado.api.constant.doc.CreditRequestDtoConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO de respuesta detallada para solicitudes de crédito con información completa")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreditRequestResponseDTO(

        @Schema(description = CreditRequestDtoConstants.ID_DESCRIPTION,
                example = CreditRequestDtoConstants.ID_EXAMPLE)
        String id,

        @Schema(description = CreditRequestDtoConstants.AMOUNT_DESCRIPTION,
                example = CreditRequestDtoConstants.AMOUNT_EXAMPLE)
        BigDecimal amount,

        @Schema(description = CreditRequestDtoConstants.TERM_DESCRIPTION,
                example = CreditRequestDtoConstants.TERM_EXAMPLE)
        Integer term,

        @Schema(description = CreditRequestDtoConstants.DOCUMENT_NUMBER_DESCRIPTION,
                example = CreditRequestDtoConstants.DOCUMENT_NUMBER_EXAMPLE)
        String documentNumber,

        @Schema(description = CreditRequestDtoConstants.FULL_NAME_DESCRIPTION,
                example = CreditRequestDtoConstants.FULL_NAME_EXAMPLE)
        String fullName,

        @Schema(description = CreditRequestDtoConstants.EMAIL_DESCRIPTION,
                example = CreditRequestDtoConstants.EMAIL_EXAMPLE)
        String email,

        @Schema(description = CreditRequestDtoConstants.STATUS_DESCRIPTION,
                example = CreditRequestDtoConstants.STATUS_EXAMPLE)
        String status,

        @Schema(description = CreditRequestDtoConstants.TYPE_DESCRIPTION,
                example = CreditRequestDtoConstants.TYPE_EXAMPLE)
        String creditType,

        @Schema(description = CreditRequestDtoConstants.INTEREST_RATE_DESCRIPTION,
                example = CreditRequestDtoConstants.INTEREST_RATE_EXAMPLE)
        BigDecimal interestRate,

        @Schema(description = CreditRequestDtoConstants.BASE_SALARY_DESCRIPTION,
                example = CreditRequestDtoConstants.BASE_SALARY_EXAMPLE)
        BigDecimal baseSalary,

        @Schema(description = CreditRequestDtoConstants.MONTHLY_PAYMENT_DESCRIPTION,
                example = CreditRequestDtoConstants.MONTHLY_PAYMENT_EXAMPLE)
        BigDecimal monthlyPayment
) {
}