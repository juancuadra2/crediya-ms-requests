package co.com.jcuadrado.api.dto.request;

import co.com.jcuadrado.api.constant.validation.RegexPatterns;
import co.com.jcuadrado.api.constant.doc.CreditRequestDtoConstants;
import co.com.jcuadrado.api.constant.validation.CreditRequestValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Schema(description = CreditRequestDtoConstants.CREATE_CREDIT_REQUEST_DESCRIPTION)
public record CreateCreditRequestDTO(

        @Schema(description = CreditRequestDtoConstants.AMOUNT_DESCRIPTION, 
                example = CreditRequestDtoConstants.AMOUNT_EXAMPLE,
                minimum = CreditRequestValidationConstants.AMOUNT_MIN_VALUE)
        @NotNull(message = CreditRequestValidationConstants.AMOUNT_NOT_NULL_MESSAGE)
        @DecimalMin(value = CreditRequestValidationConstants.AMOUNT_MIN_VALUE, message = CreditRequestValidationConstants.AMOUNT_MIN_MESSAGE)
        BigDecimal amount,

        @Schema(description = CreditRequestDtoConstants.LIMIT_DATE_DESCRIPTION,
                example = CreditRequestDtoConstants.LIMIT_DATE_EXAMPLE)
        @NotNull(message = CreditRequestValidationConstants.LIMIT_DATE_NOT_NULL_MESSAGE)
        @Future(message = CreditRequestValidationConstants.LIMIT_DATE_FUTURE_MESSAGE)
        LocalDate limitDate,

        @Schema(description = CreditRequestDtoConstants.DOCUMENT_NUMBER_DESCRIPTION,
                example = CreditRequestDtoConstants.DOCUMENT_NUMBER_EXAMPLE,
                pattern = RegexPatterns.DOCUMENT_NUMBER_PATTERN)
        @NotBlank(message = CreditRequestValidationConstants.DOCUMENT_NUMBER_NOT_BLANK_MESSAGE)
        @Pattern(regexp = RegexPatterns.DOCUMENT_NUMBER_PATTERN, message = CreditRequestValidationConstants.DOCUMENT_NUMBER_PATTERN_MESSAGE)
        String documentNumber,

        @Schema(description = CreditRequestDtoConstants.CREDIT_TYPE_DESCRIPTION,
                example = CreditRequestDtoConstants.CREDIT_TYPE_EXAMPLE)
        @NotBlank(message = CreditRequestValidationConstants.CREDIT_TYPE_NOT_BLANK_MESSAGE)
        String creditType

) {
}
