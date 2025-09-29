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
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.NoArgsConstructor;

/**
 * Utility class for creating the GET operation to retrieve credit requests in OpenAPI documentation.
 * Contains all the logic needed for the credit request retrieval endpoint including
 * query parameters, responses, and examples.
 * Follows the Single Responsibility Principle by focusing only on GET operation configuration.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GetCreditRequestOperationUtil {

    /**
     * Creates the complete GET operation for retrieving credit requests.
     * Includes query parameters, all possible responses, and complete documentation.
     * 
     * @return Operation configured for credit request retrieval
     */
    public static Operation createOperation() {
        return new Operation()
                .summary(OpenApiConfigConstants.CREDIT_REQUEST_LIST_SUMMARY)
                .description(OpenApiConfigConstants.CREDIT_REQUEST_LIST_DESCRIPTION)
                .operationId(OpenApiConfigConstants.CREDIT_REQUEST_LIST_OPERATION_ID)
                .addTagsItem(OpenApiConfigConstants.CREDIT_REQUEST_TAG_NAME)
                .addParametersItem(createPageParameter())
                .addParametersItem(createSizeParameter())
                .addParametersItem(createFilterParameter())
                .addParametersItem(createStatusParameter())
                .responses(createResponses());
    }

    /**
     * Creates the page query parameter for pagination.
     * 
     * @return Parameter configured for page specification
     */
    private static Parameter createPageParameter() {
        return new Parameter()
                .name(OpenApiConfigConstants.PAGE_PARAM_NAME)
                .in(OpenApiConfigConstants.PARAM_IN_QUERY)
                .description(OpenApiConfigConstants.PAGE_PARAM_DESCRIPTION)
                .required(false)
                .schema(new Schema<Integer>()
                        .type(OpenApiConfigConstants.PARAM_TYPE_INTEGER)
                        .example(OpenApiConfigConstants.PAGE_EXAMPLE));
    }

    /**
     * Creates the size query parameter for pagination.
     * 
     * @return Parameter configured for page size specification
     */
    private static Parameter createSizeParameter() {
        return new Parameter()
                .name(OpenApiConfigConstants.SIZE_PARAM_NAME)
                .in(OpenApiConfigConstants.PARAM_IN_QUERY)
                .description(OpenApiConfigConstants.SIZE_PARAM_DESCRIPTION)
                .required(false)
                .schema(new Schema<Integer>()
                        .type(OpenApiConfigConstants.PARAM_TYPE_INTEGER)
                        .example(OpenApiConfigConstants.SIZE_EXAMPLE));
    }

    /**
     * Creates the filter query parameter for searching.
     * 
     * @return Parameter configured for filtering specification
     */
    private static Parameter createFilterParameter() {
        return new Parameter()
                .name(OpenApiConfigConstants.FILTER_PARAM_NAME)
                .in(OpenApiConfigConstants.PARAM_IN_QUERY)
                .description(OpenApiConfigConstants.FILTER_PARAM_DESCRIPTION)
                .required(false)
                .schema(new Schema<String>()
                        .type(OpenApiConfigConstants.PARAM_TYPE_STRING)
                        .example(OpenApiConfigConstants.FILTER_EXAMPLE));
    }

    /**
     * Creates the status query parameter for filtering by status.
     * 
     * @return Parameter configured for status filtering specification
     */
    private static Parameter createStatusParameter() {
        return new Parameter()
                .name(OpenApiConfigConstants.STATUS_PARAM_NAME)
                .in(OpenApiConfigConstants.PARAM_IN_QUERY)
                .description(OpenApiConfigConstants.STATUS_PARAM_DESCRIPTION)
                .required(false)
                .schema(new Schema<String>()
                        .type(OpenApiConfigConstants.PARAM_TYPE_STRING)
                        .example(OpenApiConfigConstants.STATUS_EXAMPLE));
    }

    /**
     * Creates all possible responses for the credit request list endpoint.
     * 
     * @return ApiResponses configured for GET operations
     */
    private static ApiResponses createResponses() {
        return new ApiResponses()
                .addApiResponse(SchemaConstants.STATUS_200, createSuccessResponse())
                .addApiResponse(SchemaConstants.STATUS_401, createUnauthorizedResponse())
                .addApiResponse(SchemaConstants.STATUS_403, createForbiddenResponse())
                .addApiResponse(SchemaConstants.STATUS_500, createInternalErrorResponse());
    }

    /**
     * Creates the 200 OK success response for credit request list.
     * 
     * @return ApiResponse for successful retrieval
     */
    private static ApiResponse createSuccessResponse() {
        return new ApiResponse()
                .description(ResponseConstants.CREDIT_REQUEST_LIST_SUCCESS_DESCRIPTION)
                .content(new Content()
                        .addMediaType(SchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(SchemaConstants.COMPONENT_SCHEMA_PAGE_RESPONSE_CREDIT_REQUEST_RESPONSE))
                                .addExamples(OpenApiConfigConstants.SUCCESS_LIST_RESPONSE_NAME,
                                        OpenApiUtil.createExample(
                                                ResponseConstants.CREDIT_REQUEST_LIST_SUCCESS_SUMMARY,
                                                ExampleConstants.CREDIT_REQUEST_LIST_RESPONSE_EXAMPLE))));
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
     * Creates the 403 Forbidden error response.
     * 
     * @return ApiResponse for authorization errors
     */
    private static ApiResponse createForbiddenResponse() {
        return new ApiResponse()
                .description(SecurityConfigConstants.FORBIDDEN_DESCRIPTION)
                .content(new Content()
                        .addMediaType(SchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(SchemaConstants.COMPONENT_SCHEMA_ERROR_RESPONSE_DTO))
                                .addExamples(SecurityConfigConstants.FORBIDDEN_EXAMPLE_NAME,
                                        OpenApiUtil.createExample(
                                                SecurityConfigConstants.FORBIDDEN_EXAMPLE_SUMMARY,
                                                ExampleConstants.FORBIDDEN_ERROR_EXAMPLE))));
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