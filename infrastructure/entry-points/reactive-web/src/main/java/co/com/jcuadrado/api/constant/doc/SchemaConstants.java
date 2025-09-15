package co.com.jcuadrado.api.constant.doc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SchemaConstants {

    // Media Types
    public static final String APPLICATION_JSON = "application/json";

    // HTTP Status Codes
    public static final String STATUS_200 = "200";
    public static final String STATUS_201 = "201";
    public static final String STATUS_400 = "400";
    public static final String STATUS_401 = "401";
    public static final String STATUS_403 = "403";
    public static final String STATUS_500 = "500";

    // Component Schema References
    public static final String COMPONENT_SCHEMA_ERROR_RESPONSE_DTO = "#/components/schemas/ErrorResponseDTO";
    public static final String COMPONENT_SCHEMA_CREATE_CREDIT_REQUEST_DTO = "#/components/schemas/CreateCreditRequestDTO";
    public static final String COMPONENT_SCHEMA_CREDIT_REQUEST_DTO = "#/components/schemas/CreditRequestDTO";
    public static final String COMPONENT_SCHEMA_PAGE_RESPONSE_CREDIT_REQUEST_RESPONSE = "#/components/schemas/PageResponseCreditRequestResponse";
    public static final String COMPONENT_SCHEMA_CREDIT_REQUEST_RESPONSE = "#/components/schemas/CreditRequestResponseDTO";
    
    // Schema Types
    public static final String SCHEMA_TYPE_OBJECT = "object";
    public static final String SCHEMA_TYPE_ARRAY = "array";
    public static final String SCHEMA_TYPE_INTEGER = "integer";
    public static final String SCHEMA_TYPE_STRING = "string";
    public static final String SCHEMA_FORMAT_INT64 = "int64";
    
    // Schema Property Names
    public static final String PROPERTY_CONTENT = "content";
    public static final String PROPERTY_PAGE = "page";
    public static final String PROPERTY_SIZE = "size";
    public static final String PROPERTY_TOTAL_ELEMENTS = "totalElements";
    public static final String PROPERTY_TOTAL_PAGES = "totalPages";
    
    // Schema Property Examples
    public static final Integer PROPERTY_PAGE_EXAMPLE = 1;
    public static final Integer PROPERTY_SIZE_EXAMPLE = 10;
    public static final Long PROPERTY_TOTAL_ELEMENTS_EXAMPLE = 2L;
    public static final Integer PROPERTY_TOTAL_PAGES_EXAMPLE = 1;
}
