package co.com.jcuadrado.api.config;

import co.com.jcuadrado.api.constant.api.EndpointConstants;
import co.com.jcuadrado.api.constant.doc.ExampleConstants;
import co.com.jcuadrado.api.constant.doc.ResponseConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiConfigConstants;
import co.com.jcuadrado.api.constant.doc.SchemaConstants;
import co.com.jcuadrado.api.constant.doc.SecurityConfigConstants;
import co.com.jcuadrado.api.constant.doc.ServerConfigConstants;
import co.com.jcuadrado.api.dto.ErrorResponseDTO;
import co.com.jcuadrado.api.dto.request.CreateCreditRequestDTO;
import co.com.jcuadrado.api.dto.request.CreditRequestDTO;
import co.com.jcuadrado.api.util.OpenApiUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for OpenAPI documentation.
 * Centralizes all API documentation including paths, operations, and responses.
 * Follows the Single Responsibility Principle by managing only OpenAPI setup.
 * Implements the Open/Closed Principle allowing extension without modification.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(createApiInfo())
                .servers(createServerList())
                .addTagsItem(createCreditRequestTag())
                .components(createComponents())
                .addSecurityItem(new SecurityRequirement().addList(SecurityConfigConstants.BEARER_AUTH_SCHEME_NAME))
                .path(EndpointConstants.REQUEST_API_PATH, createCreditRequestPathItem());
    }

    /**
     * Creates comprehensive API information following documentation best practices.
     */
    private Info createApiInfo() {
        return new Info()
                .title(OpenApiConfigConstants.API_TITLE)
                .version(OpenApiConfigConstants.API_VERSION)
                .description(OpenApiConfigConstants.API_DESCRIPTION)
                .contact(createContact())
                .license(createLicense());
    }

    /**
     * Creates contact information for API maintainers.
     */
    private Contact createContact() {
        return new Contact()
                .name(ServerConfigConstants.CONTACT_NAME)
                .email(ServerConfigConstants.CONTACT_EMAIL);
    }

    /**
     * Creates license information for the API.
     */
    private License createLicense() {
        return new License()
                .name(ServerConfigConstants.LICENSE_NAME)
                .url(ServerConfigConstants.LICENSE_URL);
    }

    /**
     * Creates server configuration for different environments.
     */
    private List<Server> createServerList() {
        return List.of(
                new Server()
                        .url(ServerConfigConstants.DEVELOPMENT_SERVER_URL)
                        .description(ServerConfigConstants.DEVELOPMENT_SERVER_DESCRIPTION)
        );
    }

    /**
     * Creates the Credit Request tag for organizing endpoints.
     */
    private Tag createCreditRequestTag() {
        return new Tag()
                .name(OpenApiConfigConstants.CREDIT_REQUEST_TAG_NAME)
                .description(OpenApiConfigConstants.CREDIT_REQUEST_TAG_DESCRIPTION);
    }

    /**
     * Creates components with all necessary schemas and security schemes.
     * Follows the Dependency Inversion Principle by using utility methods.
     */
    private Components createComponents() {
        Components components = new Components();

        // Add all DTO schemas
        OpenApiUtil.addSchemaToComponents(components, CreateCreditRequestDTO.class);
        OpenApiUtil.addSchemaToComponents(components, CreditRequestDTO.class);
        OpenApiUtil.addSchemaToComponents(components, ErrorResponseDTO.class);

        // Add security schemes
        components.addSecuritySchemes(SecurityConfigConstants.BEARER_AUTH_SCHEME_NAME, createBearerAuthScheme());

        return components;
    }

    /**
     * Creates the Bearer authentication security scheme.
     */
    private SecurityScheme createBearerAuthScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme(SecurityConfigConstants.BEARER_SCHEME)
                .bearerFormat(SecurityConfigConstants.BEARER_FORMAT)
                .description(SecurityConfigConstants.BEARER_DESCRIPTION);
    }

    /**
     * Creates the complete path item for credit request operations.
     */
    private PathItem createCreditRequestPathItem() {
        return new PathItem()
                .post(createSaveCreditRequestOperation());
    }

    /**
     * Creates the POST operation for creating credit requests.
     * Includes complete documentation with request body, responses, and examples.
     */
    private Operation createSaveCreditRequestOperation() {
        return new Operation()
                .summary(OpenApiConfigConstants.CREDIT_REQUEST_CREATE_SUMMARY)
                .description(OpenApiConfigConstants.CREDIT_REQUEST_CREATE_DESCRIPTION)
                .operationId(OpenApiConfigConstants.CREDIT_REQUEST_CREATE_OPERATION_ID)
                .addTagsItem(OpenApiConfigConstants.CREDIT_REQUEST_TAG_NAME)
                .requestBody(createCreditRequestBody())
                .responses(createCreditRequestResponses());
    }

    /**
     * Creates the request body specification for credit request creation.
     */
    private RequestBody createCreditRequestBody() {
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
     */
    private ApiResponses createCreditRequestResponses() {
        return new ApiResponses()
                .addApiResponse(SchemaConstants.STATUS_201, createSuccessResponse())
                .addApiResponse(SchemaConstants.STATUS_400, createBadRequestResponse())
                .addApiResponse(SchemaConstants.STATUS_401, createUnauthorizedResponse())
                .addApiResponse(SchemaConstants.STATUS_500, createInternalErrorResponse());
    }

    /**
     * Creates the 201 Created success response.
     */
    private ApiResponse createSuccessResponse() {
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
     */
    private ApiResponse createBadRequestResponse() {
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
     */
    private ApiResponse createUnauthorizedResponse() {
        return new ApiResponse()
                .description(SecurityConfigConstants.UNAUTHORIZED_DESCRIPTION)
                .content(new Content()
                        .addMediaType(SchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(SchemaConstants.COMPONENT_SCHEMA_ERROR_RESPONSE_DTO))
                                .addExamples(SecurityConfigConstants.UNAUTHORIZED_EXAMPLE_NAME,
                                        OpenApiUtil.createExample(
                                                SecurityConfigConstants.UNAUTHORIZED_EXAMPLE_SUMMARY,
                                                SecurityConfigConstants.UNAUTHORIZED_EXAMPLE_VALUE))));
    }

    /**
     * Creates the 500 Internal Server Error response.
     */
    private ApiResponse createInternalErrorResponse() {
        return OpenApiUtil.createInternalServerErrorResponse();
    }
}
