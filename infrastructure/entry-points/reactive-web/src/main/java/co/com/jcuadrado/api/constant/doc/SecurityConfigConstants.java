package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityConfigConstants {

    // Security Scheme Configuration
    public static final String BEARER_AUTH_SCHEME_NAME = "bearerAuth";
    public static final String BEARER_SCHEME = "bearer";
    public static final String BEARER_FORMAT = "JWT";
    public static final String BEARER_DESCRIPTION = "Autenticación JWT. Formato: Bearer {token}";

    // Authentication Error Messages
    public static final String UNAUTHORIZED_DESCRIPTION = "Token de autenticación inválido o ausente";
    public static final String UNAUTHORIZED_EXAMPLE_NAME = "unauthorized_error";
    public static final String UNAUTHORIZED_EXAMPLE_SUMMARY = "Error de autenticación";
    public static final String UNAUTHORIZED_EXAMPLE_VALUE = "{\n  \"timestamp\": \"2024-01-15T10:30:00Z\",\n  \"status\": 401,\n  \"error\": \"Unauthorized\",\n  \"message\": \"Token de autenticación inválido o ausente\",\n  \"path\": \"/api/v1/credit-requests\"\n}";
}