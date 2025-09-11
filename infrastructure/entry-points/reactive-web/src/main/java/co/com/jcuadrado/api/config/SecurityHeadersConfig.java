package co.com.jcuadrado.api.config;

import co.com.jcuadrado.api.constant.security.SecurityHeaderConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
public class SecurityHeadersConfig implements WebFilter {

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
        headers.set(SecurityHeaderConstants.CSP_NAME, SecurityHeaderConstants.CSP_VALUE);
        headers.set(SecurityHeaderConstants.STS_NAME, SecurityHeaderConstants.STS_VALUE);
        headers.set(SecurityHeaderConstants.X_CONTENT_TYPE_OPTIONS_NAME, SecurityHeaderConstants.X_CONTENT_TYPE_OPTIONS_VALUE);
        headers.set(SecurityHeaderConstants.SERVER_NAME, SecurityHeaderConstants.SERVER_VALUE);
        headers.set(SecurityHeaderConstants.CACHE_CONTROL_NAME, SecurityHeaderConstants.CACHE_CONTROL_VALUE);
        headers.set(SecurityHeaderConstants.PRAGMA_NAME, SecurityHeaderConstants.PRAGMA_VALUE);
        headers.set(SecurityHeaderConstants.REFERRER_POLICY_NAME, SecurityHeaderConstants.REFERRER_POLICY_VALUE);
        return chain.filter(exchange);
    }
}