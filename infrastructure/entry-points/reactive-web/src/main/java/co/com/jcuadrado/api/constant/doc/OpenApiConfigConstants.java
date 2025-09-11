package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenApiConfigConstants {

    // API Information
    public static final String API_TITLE = "Credit Requests Management API";
    public static final String API_VERSION = "1.0";
    public static final String API_DESCRIPTION = "API REST para la gestión de solicitudes de crédito - Sistema Crediya";

    // Operation Tags
    public static final String CREDIT_REQUEST_TAG_NAME = "Credit Requests";
    public static final String CREDIT_REQUEST_TAG_DESCRIPTION = "Operaciones para la gestión de solicitudes de crédito";

    // Operation Details
    public static final String CREDIT_REQUEST_CREATE_SUMMARY = "Crear nueva solicitud de crédito";
    public static final String CREDIT_REQUEST_CREATE_DESCRIPTION = "Crea una nueva solicitud de crédito con los datos proporcionados";
    public static final String CREDIT_REQUEST_CREATE_OPERATION_ID = "createCreditRequest";

    // Response Names
    public static final String SUCCESS_RESPONSE_NAME = "success";
    public static final String VALIDATION_ERROR_NAME = "validationError";
}
