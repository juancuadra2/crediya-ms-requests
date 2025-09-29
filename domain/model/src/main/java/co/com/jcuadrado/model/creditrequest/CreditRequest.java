package co.com.jcuadrado.model.creditrequest;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreditRequest {
    private String id;
    private BigDecimal amount;
    private Integer term;
    private String documentNumber;
    private String email;
    private String status;
    private String creditType;
}
