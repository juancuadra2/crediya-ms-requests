package co.com.jcuadrado.api.security;

import co.com.jcuadrado.api.constant.auth.AuthConstants;
import co.com.jcuadrado.api.constant.auth.PublicPathConstants;
import co.com.jcuadrado.api.exception.AuthException;
import co.com.jcuadrado.usecase.auth.TokenManagerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {

    private final TokenManagerUseCase tokenManager;

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        if (isPublicPath(exchange)) {
            return chain.filter(exchange);
        }
        return extractToken(exchange.getRequest())
                .flatMap(this::validateAndProcessToken)
                .flatMap(token -> authenticateAndContinue(token, exchange, chain));
    }

    private boolean isPublicPath(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();
        return path.startsWith(PublicPathConstants.SWAGGER_UI)
                || path.startsWith(PublicPathConstants.V3_API_DOCS)
                || path.startsWith(PublicPathConstants.SWAGGER_RESOURCES)
                || path.startsWith(PublicPathConstants.WEBJARS)
                || path.startsWith(PublicPathConstants.ACTUATOR)
                || path.startsWith(PublicPathConstants.H2) ;
    }

    private Mono<String> extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null) {
            return Mono.error(new AuthException(AuthException.ErrorType.UNAUTHORIZED, AuthConstants.NO_TOKEN_PROVIDED_ERROR));
        }

        if (!authHeader.startsWith(AuthConstants.BEARER_PREFIX)) {
            return Mono.error(new AuthException(AuthException.ErrorType.UNAUTHORIZED, AuthConstants.INVALID_TOKEN_FORMAT_ERROR));
        }

        return Mono.just(authHeader.replace(AuthConstants.BEARER_PREFIX, ""));
    }

    private Mono<String> validateAndProcessToken(String token) {
        return tokenManager.validateToken(token)
                .filter(Boolean::booleanValue)
                .map(valid -> token)
                .switchIfEmpty(Mono.error(new AuthException(AuthException.ErrorType.UNAUTHORIZED, AuthConstants.INVALID_TOKEN_ERROR)));
    }

    private Mono<Void> authenticateAndContinue(String token, ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getAttributes().put(AuthConstants.TOKEN_ATTRIBUTE, token);
        return chain.filter(exchange);
    }

}
