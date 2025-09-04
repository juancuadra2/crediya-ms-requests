package co.com.jcuadrado.api.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCreditRequestDTO(
        BigDecimal amount,
        LocalDate limitDate,
        String documentNumber,
        String creditType
) {
}
