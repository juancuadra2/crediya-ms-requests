package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExampleConstants {

    // Error Response Examples
    public static final String UNAUTHORIZED_ERROR_EXAMPLE = """
            {
              "timestamp": "2024-01-15T10:30:00Z",
              "status": 401,
              "error": "Unauthorized",
              "message": "Token de autenticación inválido o ausente",
              "path": "/api/requests"
            }
            """;

    public static final String FORBIDDEN_ERROR_EXAMPLE = """
            {
              "messages": ["Acceso denegado"],
              "error": "Forbidden",
              "status": "FORBIDDEN"
            }
            """;

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
              "term": 12,
              "documentNumber": "12345678",
              "creditType": "PERSONAL"
            }
            """;

    // Response Examples
    public static final String CREDIT_REQUEST_CREATED_RESPONSE_EXAMPLE = """
            {
              "amount": 1500000.00,
              "term": 12,
              "documentNumber": "12345678",
              "type": "PERSONAL",
              "status": "PENDING"
            }
            """;

    // Credit Request List Response Examples
    public static final String CREDIT_REQUEST_LIST_RESPONSE_EXAMPLE = """
            {
              "content": [
                {
                  "id": "123e4567-e89b-12d3-a456-426614174000",
                  "amount": 1500000.00,
                  "term": 12,
                  "documentNumber": "12345678",
                  "fullName": "Juan Carlos Pérez López",
                  "email": "juan.perez@email.com",
                  "status": "PENDING",
                  "creditType": "PERSONAL",
                  "interestRate": 1.2,
                  "baseSalary": 3500000.00,
                  "monthlyPayment": 145000.00
                },
                {
                  "id": "987f6543-e21c-34d5-b678-987654321098",
                  "amount": 2000000.00,
                  "term": 24,
                  "documentNumber": "87654321",
                  "fullName": "María Fernanda García Ruiz",
                  "email": "maria.garcia@email.com",
                  "status": "APPROVED",
                  "creditType": "HOUSING",
                  "interestRate": 0.95,
                  "baseSalary": 4200000.00,
                  "monthlyPayment": 95000.00
                }
              ],
              "page": 1,
              "size": 10,
              "totalElements": 2,
              "totalPages": 1
            }
            """;
}
