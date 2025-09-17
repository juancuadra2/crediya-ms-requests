package co.com.jcuadrado.api.dto.request;

import co.com.jcuadrado.api.constant.validation.CreditRequestValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusCreditRequestDto (
        @NotBlank(message = CreditRequestValidationConstants.STATUS_REQUIRED_MESSAGE)
        @NotNull(message = CreditRequestValidationConstants.STATUS_REQUIRED_MESSAGE)
        String status
) {}
