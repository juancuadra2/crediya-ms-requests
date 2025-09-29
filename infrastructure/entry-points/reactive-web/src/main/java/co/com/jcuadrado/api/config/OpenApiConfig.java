package co.com.jcuadrado.api.config;

import co.com.jcuadrado.api.constant.api.EndpointConstants;
import co.com.jcuadrado.api.constant.doc.OpenApiConfigConstants;
import co.com.jcuadrado.api.constant.doc.SecurityConfigConstants;
import co.com.jcuadrado.api.constant.doc.ServerConfigConstants;
import co.com.jcuadrado.api.dto.ErrorResponseDTO;
import co.com.jcuadrado.api.dto.request.CreateCreditRequestDTO;
import co.com.jcuadrado.api.dto.request.CreditRequestDTO;
import co.com.jcuadrado.api.dto.response.CreditRequestResponseDTO;
import co.com.jcuadrado.api.dto.response.PageResponseDTO;
import co.com.jcuadrado.api.util.CreateCreditRequestOperationUtil;
import co.com.jcuadrado.api.util.GetCreditRequestOperationUtil;
import co.com.jcuadrado.api.util.OpenApiUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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
        OpenApiUtil.addSchemaToComponents(components, CreditRequestResponseDTO.class);
        OpenApiUtil.addSchemaToComponents(components, PageResponseDTO.class);

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
                .post(CreateCreditRequestOperationUtil.createOperation())
                .get(GetCreditRequestOperationUtil.createOperation());
    }

}
