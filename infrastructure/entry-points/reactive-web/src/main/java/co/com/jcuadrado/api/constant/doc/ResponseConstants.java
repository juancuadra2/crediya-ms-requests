package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseConstants {
    
    // Response Descriptions
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = "Error interno del servidor";
    public static final String BAD_REQUEST_DESCRIPTION = "Solicitud incorrecta";
    public static final String CREDIT_REQUEST_CREATED_DESCRIPTION = "Solicitud de crédito creada exitosamente";
    public static final String CREDIT_REQUEST_LIST_SUCCESS_DESCRIPTION = "Lista de solicitudes de crédito obtenida exitosamente";

    // Response Summaries
    public static final String INTERNAL_ERROR_SUMMARY = "Error del servidor";
    public static final String VALIDATION_ERROR_SUMMARY = "Datos inválidos en la solicitud";
    public static final String CREDIT_REQUEST_SUCCESS_SUMMARY = "Solicitud de crédito procesada correctamente";
    public static final String CREDIT_REQUEST_LIST_SUCCESS_SUMMARY = "Lista de solicitudes obtenida correctamente";

    // Response Names/Titles
    public static final String INTERNAL_ERROR_NAME = "Error interno";
}
