package co.com.jcuadrado.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusCreditRequestDto (
        @NotBlank
        @NotNull
        String status
) {}
