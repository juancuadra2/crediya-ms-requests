package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExampleConstants {

    // Error Response Examples
    public static final String INTERNAL_SERVER_ERROR_RESPONSE_EXAMPLE = """
            {
              "messages": ["Error interno del servidor"],
              "error": "Internal Server Error",
              "status": "INTERNAL_SERVER_ERROR"
            }
            """;

    public static final String CREDIT_REQUEST_VALIDATION_ERROR_EXAMPLE = """
            {
              "messages": [
                "El monto debe ser mayor a 0",
                "La fecha límite no puede ser anterior a la fecha actual",
                "El número de documento es requerido"
              ],
              "error": "Validation Error",
              "status": "BAD_REQUEST"
            }
            """;

    // Request Examples
    public static final String CREATE_CREDIT_REQUEST_EXAMPLE = """
            {
              "amount": 1500000.00,
              "limitDate": "2025-12-31",
              "documentNumber": "12345678",
              "creditType": "PERSONAL"
            }
            """;

    // Response Examples
    public static final String CREDIT_REQUEST_CREATED_RESPONSE_EXAMPLE = """
            {
              "amount": 1500000.00,
              "limitDate": "2025-12-31",
              "documentNumber": "12345678",
              "type": "PERSONAL",
              "status": "PENDING"
            }
            """;
}
