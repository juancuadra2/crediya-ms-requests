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
    
    public static final String CREDIT_REQUEST_LIST_SUMMARY = "Obtener solicitudes de crédito";
    public static final String CREDIT_REQUEST_LIST_DESCRIPTION = "Obtiene una lista paginada de solicitudes de crédito con filtros opcionales";
    public static final String CREDIT_REQUEST_LIST_OPERATION_ID = "getCreditRequests";

    // Response Names
    public static final String SUCCESS_RESPONSE_NAME = "success";
    public static final String VALIDATION_ERROR_NAME = "validationError";
    public static final String SUCCESS_LIST_RESPONSE_NAME = "successList";

    // Request Body Descriptions
    public static final String CREDIT_REQUEST_BODY_DESCRIPTION = "Datos para crear una nueva solicitud de crédito";
    public static final String CREDIT_REQUEST_EXAMPLE_SUMMARY = "Ejemplo de solicitud de crédito";
    
    // Request Parameters Descriptions
    public static final String FILTER_PARAM_DESCRIPTION = "Filtro de búsqueda por nombre completo o número de documento";
    public static final String STATUS_PARAM_DESCRIPTION = "Filtrar por estado de la solicitud";
    public static final String PAGE_PARAM_DESCRIPTION = "Número de página para la paginación (inicia en 1)";
    public static final String SIZE_PARAM_DESCRIPTION = "Cantidad de elementos por página";
    
    // Parameter Names
    public static final String PAGE_PARAM_NAME = "page";
    public static final String SIZE_PARAM_NAME = "size";
    public static final String FILTER_PARAM_NAME = "filter";
    public static final String STATUS_PARAM_NAME = "status";
    
    // Parameter Types
    public static final String PARAM_TYPE_INTEGER = "integer";
    public static final String PARAM_TYPE_STRING = "string";
    public static final String PARAM_IN_QUERY = "query";
    
    // Parameter Default Values and Examples
    public static final Integer PAGE_DEFAULT_VALUE = 1;
    public static final Integer SIZE_DEFAULT_VALUE = 10;
    public static final Integer PAGE_EXAMPLE = 1;
    public static final Integer SIZE_EXAMPLE = 10;
    public static final String FILTER_EXAMPLE = "Juan Pérez";
    public static final String STATUS_EXAMPLE = "PENDING";
}
