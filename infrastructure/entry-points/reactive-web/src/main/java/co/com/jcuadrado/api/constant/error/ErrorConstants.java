package co.com.jcuadrado.api.constant.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorConstants {

    // Validation error messages
    public static final String VALIDATION_FAILED = "Validación fallida";
    
    // Business error messages
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Se ha producido un error interno.";
    public static final String INTERNAL_SERVER_ERROR_TITLE = "Error interno";
    public static final String SERIALIZATION_EXCEPTION_MESSAGE = "Se ha producido un error al serializar la respuesta.";
}
