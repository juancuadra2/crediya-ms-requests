package co.com.jcuadrado.r2dbc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditRequestResultDTO {
    private UUID id;
    private BigDecimal amount;
    private Integer term;
    private String documentNumber;
    private String fullName;
    private String email;
    private BigDecimal baseSalary;
    private String statusName;
    private String typeName;
    private BigDecimal interestRate;
}