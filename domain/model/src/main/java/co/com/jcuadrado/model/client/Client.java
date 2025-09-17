package co.com.jcuadrado.model.client;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Client {
    private String id;
    private String documentNumber;
    private String name;
    private String lastName;
    private String email;
    private BigDecimal baseSalary;
}
