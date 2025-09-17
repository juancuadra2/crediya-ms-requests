package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.doc.ExampleConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiConfigConstants;
import co.com.jcuadrado.api.constant.doc.ResponseConstants;
import co.com.jcuadrado.api.constant.doc.SchemaConstants;
import co.com.jcuadrado.api.constant.doc.SecurityConfigConstants;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.NoArgsConstructor;

/**
 * Utility class for creating the POST operation to create credit requests in OpenAPI documentation.
 * Contains all the logic needed for the credit request creation endpoint including
 * request body, responses, and examples.
 * Follows the Single Responsibility Principle by focusing only on CREATE operation configuration.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CreateCreditRequestOperationUtil {

    /**
     * Creates the complete POST operation for creating credit requests.
     * Includes request body, all possible responses, and complete documentation.
     * 
     * @return Operation configured for credit request creation
     */
    public static Operation createOperation() {
        return new Operation()
                .summary(OpenApiConfigConstants.CREDIT_REQUEST_CREATE_SUMMARY)
                .description(OpenApiConfigConstants.CREDIT_REQUEST_CREATE_DESCRIPTION)
                .operationId(OpenApiConfigConstants.CREDIT_REQUEST_CREATE_OPERATION_ID)
                .addTagsItem(OpenApiConfigConstants.CREDIT_REQUEST_TAG_NAME)
                .requestBody(createRequestBody())
                .responses(createResponses());
    }

    /**
     * Creates the request body specification for credit request creation.
     * Includes schema reference and example data.
     * 
     * @return RequestBody configured with schema and examples
     */
    private static RequestBody createRequestBody() {
        return new RequestBody()
                .description(OpenApiConfigConstants.CREDIT_REQUEST_BODY_DESCRIPTION)
                .required(true)
                .content(new Content()
                        .addMediaType(SchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(SchemaConstants.COMPONENT_SCHEMA_CREATE_CREDIT_REQUEST_DTO))
                                .addExamples(OpenApiConfigConstants.SUCCESS_RESPONSE_NAME,
                                        OpenApiUtil.createExample(
                                                OpenApiConfigConstants.CREDIT_REQUEST_EXAMPLE_SUMMARY,
                                                ExampleConstants.CREATE_CREDIT_REQUEST_EXAMPLE))));
    }

    /**
     * Creates all possible responses for the credit request creation endpoint.
     * 
     * @return ApiResponses configured for POST operations
     */
    private static ApiResponses createResponses() {
        return new ApiResponses()
                .addApiResponse(SchemaConstants.STATUS_201, createSuccessResponse())
                .addApiResponse(SchemaConstants.STATUS_400, createBadRequestResponse())
                .addApiResponse(SchemaConstants.STATUS_401, createUnauthorizedResponse())
                .addApiResponse(SchemaConstants.STATUS_500, createInternalErrorResponse());
    }

    /**
     * Creates the 201 Created success response.
     * 
     * @return ApiResponse for successful creation
     */
    private static ApiResponse createSuccessResponse() {
        return new ApiResponse()
                .description(ResponseConstants.CREDIT_REQUEST_CREATED_DESCRIPTION)
                .content(new Content()
                        .addMediaType(SchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(SchemaConstants.COMPONENT_SCHEMA_CREDIT_REQUEST_DTO))
                                .addExamples(OpenApiConfigConstants.SUCCESS_RESPONSE_NAME,
                                        OpenApiUtil.createExample(
                                                ResponseConstants.CREDIT_REQUEST_SUCCESS_SUMMARY,
                                                ExampleConstants.CREDIT_REQUEST_CREATED_RESPONSE_EXAMPLE))));
    }

    /**
     * Creates the 400 Bad Request error response.
     * 
     * @return ApiResponse for validation errors
     */
    private static ApiResponse createBadRequestResponse() {
        return new ApiResponse()
                .description(ResponseConstants.BAD_REQUEST_DESCRIPTION)
                .content(new Content()
                        .addMediaType(SchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(SchemaConstants.COMPONENT_SCHEMA_ERROR_RESPONSE_DTO))
                                .addExamples(OpenApiConfigConstants.VALIDATION_ERROR_NAME,
                                        OpenApiUtil.createExample(
                                                ResponseConstants.VALIDATION_ERROR_SUMMARY,
                                                ExampleConstants.CREDIT_REQUEST_VALIDATION_ERROR_EXAMPLE))));
    }

    /**
     * Creates the 401 Unauthorized error response.
     * 
     * @return ApiResponse for authentication errors
     */
    private static ApiResponse createUnauthorizedResponse() {
        return new ApiResponse()
                .description(SecurityConfigConstants.UNAUTHORIZED_DESCRIPTION)
                .content(new Content()
                        .addMediaType(SchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(SchemaConstants.COMPONENT_SCHEMA_ERROR_RESPONSE_DTO))
                                .addExamples(SecurityConfigConstants.UNAUTHORIZED_EXAMPLE_NAME,
                                        OpenApiUtil.createExample(
                                                SecurityConfigConstants.UNAUTHORIZED_EXAMPLE_SUMMARY,
                                                ExampleConstants.UNAUTHORIZED_ERROR_EXAMPLE))));
    }

    /**
     * Creates the 500 Internal Server Error response.
     * 
     * @return ApiResponse for server errors
     */
    private static ApiResponse createInternalErrorResponse() {
        return OpenApiUtil.createInternalServerErrorResponse();
    }
}