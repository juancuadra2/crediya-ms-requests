package co.com.jcuadrado.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class QueryParamUtil {
    public static  <T> T getParams(ServerRequest serverRequest, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap<String, String> queryParams = serverRequest.queryParams();
        Map<String, String> map = queryParams.toSingleValueMap();
        return objectMapper.convertValue(map, clazz);
    }
}
