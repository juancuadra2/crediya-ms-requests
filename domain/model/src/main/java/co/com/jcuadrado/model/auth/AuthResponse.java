package co.com.jcuadrado.model.auth;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String email;
    private String role;
}
