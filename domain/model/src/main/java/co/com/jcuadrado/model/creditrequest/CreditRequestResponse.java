package co.com.jcuadrado.model.creditrequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreditRequestResponse {
    private String id;
    private BigDecimal amount;
    private Integer term;
    private String documentNumber;
    private String fullName;
    private String email;
    private String status;
    private String creditType;
    private BigDecimal interestRate;
    private BigDecimal baseSalary;
    private BigDecimal monthlyPayment;
}