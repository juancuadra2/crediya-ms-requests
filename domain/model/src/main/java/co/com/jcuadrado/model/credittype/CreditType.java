package co.com.jcuadrado.model.credittype;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreditType {
    private String id;
    private String name;
    private BigDecimal maxAmount;
    private BigDecimal minAmount;
    private BigDecimal interestRate;
    private Boolean autoValidate;
}
