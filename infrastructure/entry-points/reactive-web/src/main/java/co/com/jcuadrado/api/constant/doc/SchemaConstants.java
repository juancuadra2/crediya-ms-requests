package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SchemaConstants {

    // Media Types
    public static final String APPLICATION_JSON = "application/json";

    // HTTP Status Codes
    public static final String STATUS_201 = "201";
    public static final String STATUS_400 = "400";
    public static final String STATUS_500 = "500";

    // Component Schema References
    public static final String COMPONENT_SCHEMA_ERROR_RESPONSE_DTO = "#/components/schemas/ErrorResponseDTO";
    public static final String COMPONENT_SCHEMA_CREATE_CREDIT_REQUEST_DTO = "#/components/schemas/CreateCreditRequestDTO";
    public static final String COMPONENT_SCHEMA_CREDIT_REQUEST_DTO = "#/components/schemas/CreditRequestDTO";
}
