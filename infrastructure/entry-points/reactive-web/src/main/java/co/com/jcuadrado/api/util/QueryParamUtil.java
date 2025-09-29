package co.com.jcuadrado.api.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryParamUtil {

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public static <T> T getParams(ServerRequest serverRequest, Class<T> clazz) {
        Objects.requireNonNull(serverRequest, "ServerRequest cannot be null");
        Objects.requireNonNull(clazz, "Class cannot be null");

        try {
            MultiValueMap<String, String> queryParams = serverRequest.queryParams();

            if (queryParams.isEmpty()) {
                return createEmptyInstance(clazz);
            }

            Map<String, String> map = queryParams.toSingleValueMap();
            return OBJECT_MAPPER.convertValue(map, clazz);

        } catch (Exception e) {
            throw new RuntimeException("Failed to convert query parameters to " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    private static <T> T createEmptyInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return OBJECT_MAPPER.convertValue(Collections.emptyMap(), clazz);
        }
    }
}