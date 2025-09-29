package co.com.jcuadrado.model.user;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String name;
    private String lastName;
    private String email;
    private String documentNumber;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private String role;
    private BigDecimal baseSalary;
}
