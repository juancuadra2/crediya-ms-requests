package co.com.jcuadrado.model.creditrequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreditRequestFilter {
    private int page;
    private int size;
    private String filter;
    private String status;
    private String email;
}