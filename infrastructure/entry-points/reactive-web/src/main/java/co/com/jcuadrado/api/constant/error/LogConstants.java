package co.com.jcuadrado.api.constant.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogConstants {
    
    // Exception log templates
    public static final String DOMAIN_EXCEPTION_LOG = "DOMAIN_EXCEPTION: {}";
    public static final String VALIDATION_EXCEPTION_LOG = "VALIDATION_EXCEPTION: {}";
    public static final String INTERNAL_SERVER_ERROR_LOG = "INTERNAL_SERVER_ERROR : ";
}
