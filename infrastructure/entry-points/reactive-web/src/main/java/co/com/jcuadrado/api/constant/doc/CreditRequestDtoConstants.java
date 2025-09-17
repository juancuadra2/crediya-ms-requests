package co.com.jcuadrado.api.constant.doc;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CreditRequestDtoConstants {

    // DTO descriptions for API documentation
    public static final String CREATE_CREDIT_REQUEST_DESCRIPTION = "DTO para crear una nueva solicitud de crédito";
    public static final String CREDIT_REQUEST_RESPONSE_DESCRIPTION = "DTO de respuesta con los datos de la solicitud de crédito";
    public static final String CREDIT_REQUEST_FILTER_DESCRIPTION = "DTO para filtrar y paginar solicitudes de crédito";
    public static final String PAGE_RESPONSE_DESCRIPTION = "DTO de respuesta paginada genérica";
    public static final String PAGE_CONTENT_DESCRIPTION = "Lista de elementos de la página actual";
    public static final String PAGE_NUMBER_DESCRIPTION = "Número de página actual (inicia en 1)";
    public static final String PAGE_SIZE_DESCRIPTION = "Cantidad de elementos por página";
    public static final String PAGE_TOTAL_ELEMENTS_DESCRIPTION = "Total de elementos disponibles";
    public static final String PAGE_TOTAL_PAGES_DESCRIPTION = "Total de páginas disponibles";

    // Page response examples
    public static final String PAGE_NUMBER_EXAMPLE = "1";
    public static final String PAGE_SIZE_RESPONSE_EXAMPLE = "10";
    public static final String PAGE_TOTAL_ELEMENTS_EXAMPLE = "100";
    public static final String PAGE_TOTAL_PAGES_EXAMPLE = "10";

    // Field descriptions for API documentation
    public static final String AMOUNT_DESCRIPTION = "Monto del crédito solicitado en pesos colombianos";
    public static final String TERM_DESCRIPTION = "Plazo de pago del crédito en meses.";
    public static final String DOCUMENT_NUMBER_DESCRIPTION = "Número de documento de identidad del solicitante";
    public static final String CREDIT_TYPE_DESCRIPTION = "Tipo de crédito solicitado";
    public static final String STATUS_DESCRIPTION = "Estado actual de la solicitud de crédito";
    public static final String TYPE_DESCRIPTION = "Tipo de crédito (campo de respuesta)";
    public static final String FULL_NAME_DESCRIPTION = "Nombre completo del solicitante";
    public static final String EMAIL_DESCRIPTION = "Dirección de correo electrónico del solicitante";
    public static final String INTEREST_RATE_DESCRIPTION = "Tasa de interés aplicada al crédito";
    public static final String BASE_SALARY_DESCRIPTION = "Salario base del solicitante";
    public static final String MONTHLY_PAYMENT_DESCRIPTION = "Pago mensual calculado del crédito";
    public static final String ID_DESCRIPTION = "Identificador único de la solicitud de crédito";
    public static final String PAGE_FILTER_DESCRIPTION = "Número de página para la paginación (inicia en 1)";
    public static final String SIZE_FILTER_DESCRIPTION = "Cantidad de elementos por página";
    public static final String FILTER_SEARCH_DESCRIPTION = "Filtro de búsqueda por nombre completo o número de documento";
    public static final String STATUS_FILTER_DESCRIPTION = "Filtrar por estado de la solicitud";

    // Examples for API documentation
    public static final String AMOUNT_EXAMPLE = "1500000.00";
    public static final String TERM_EXAMPLE = "12";
    public static final String DOCUMENT_NUMBER_EXAMPLE = "12345678";
    public static final String CREDIT_TYPE_EXAMPLE = "PERSONAL";
    public static final String STATUS_EXAMPLE = "PENDING";
    public static final String TYPE_EXAMPLE = "PERSONAL";
    public static final String FULL_NAME_EXAMPLE = "Juan Carlos Pérez López";
    public static final String EMAIL_EXAMPLE = "juan.perez@email.com";
    public static final String INTEREST_RATE_EXAMPLE = "1.2";
    public static final String BASE_SALARY_EXAMPLE = "3500000.00";
    public static final String MONTHLY_PAYMENT_EXAMPLE = "145000.00";
    public static final String ID_EXAMPLE = "123e4567-e89b-12d3-a456-426614174000";
    public static final String PAGE_FILTER_EXAMPLE = "1";
    public static final String SIZE_FILTER_EXAMPLE = "10";
    public static final String FILTER_SEARCH_EXAMPLE = "Juan Pérez";
    public static final String STATUS_FILTER_EXAMPLE = "PENDING";
    
    // Default values for filter DTO
    public static final String PAGE_DEFAULT_VALUE = "1";
    public static final String SIZE_DEFAULT_VALUE = "10";
}
