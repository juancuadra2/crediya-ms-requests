package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.doc.ExampleConstants;
import co.com.jcuadrado.api.constant.doc.ResponseConstants;
import co.com.jcuadrado.api.constant.doc.SchemaConstants;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OpenApiUtil {

    public static void addSchemaToComponents(Components components, Class<?> dtoClass) {
        var schemas = ModelConverters.getInstance().read(dtoClass);
        if (components.getSchemas() == null) {
            components.schemas(schemas);
        } else {
            components.getSchemas().putAll(schemas);
        }
    }

    public static ApiResponse createInternalServerErrorResponse() {
        return createErrorResponse(
                ResponseConstants.INTERNAL_SERVER_ERROR_DESCRIPTION,
                ResponseConstants.INTERNAL_ERROR_NAME,
                ResponseConstants.INTERNAL_ERROR_SUMMARY,
                ExampleConstants.INTERNAL_SERVER_ERROR_RESPONSE_EXAMPLE);
    }

    public static ApiResponse createErrorResponse(String description, String exampleName, String exampleSummary,
            Object exampleValue) {
        return new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType(SchemaConstants.APPLICATION_JSON, new MediaType()
                                .schema(new Schema<>().$ref(
                                        SchemaConstants.COMPONENT_SCHEMA_ERROR_RESPONSE_DTO))
                                .addExamples(exampleName, createExample(exampleSummary,
                                        exampleValue))));
    }

    public static Example createExample(String summary, Object value) {
        return new Example()
                .summary(summary)
                .value(value);
    }
}
