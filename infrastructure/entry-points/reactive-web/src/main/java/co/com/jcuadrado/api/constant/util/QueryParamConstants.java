package co.com.jcuadrado.api.constant.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryParamConstants {
    public static final String SERVER_REQUEST_CANNOT_BE_NULL = "ServerRequest cannot be null";
    public static final String CLASS_CANNOT_BE_NULL = "Class cannot be null";
    public static final String FAILED_CONVERT_QUERY_PARAMS = "Failed to convert query parameters to %s: %s";
}

