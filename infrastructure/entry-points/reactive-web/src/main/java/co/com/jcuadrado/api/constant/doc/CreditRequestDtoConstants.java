package co.com.jcuadrado.api.constant.doc;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CreditRequestDtoConstants {

    // DTO descriptions for API documentation
    public static final String CREATE_CREDIT_REQUEST_DESCRIPTION = "DTO para crear una nueva solicitud de crédito";
    public static final String CREDIT_REQUEST_RESPONSE_DESCRIPTION = "DTO de respuesta con los datos de la solicitud de crédito";

    // Field descriptions for API documentation
    public static final String AMOUNT_DESCRIPTION = "Monto del crédito solicitado en pesos colombianos";
    public static final String LIMIT_DATE_DESCRIPTION = "Fecha límite de pago del crédito";
    public static final String DOCUMENT_NUMBER_DESCRIPTION = "Número de documento de identidad del solicitante";
    public static final String CREDIT_TYPE_DESCRIPTION = "Tipo de crédito solicitado";
    public static final String STATUS_DESCRIPTION = "Estado actual de la solicitud de crédito";
    public static final String TYPE_DESCRIPTION = "Tipo de crédito (campo de respuesta)";

    // Examples for API documentation
    public static final String AMOUNT_EXAMPLE = "1500000.00";
    public static final String LIMIT_DATE_EXAMPLE = "2025-12-31";
    public static final String DOCUMENT_NUMBER_EXAMPLE = "12345678";
    public static final String CREDIT_TYPE_EXAMPLE = "PERSONAL";
    public static final String STATUS_EXAMPLE = "PENDING";
    public static final String TYPE_EXAMPLE = "PERSONAL";
}
