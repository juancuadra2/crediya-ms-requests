package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.auth.AuthConstants;
import co.com.jcuadrado.api.exception.AuthException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtil {

    public static Mono<String> extractToken(ServerRequest request) {
        String token = request.exchange().getAttribute(AuthConstants.TOKEN_ATTRIBUTE);

        if (token == null) {
            return Mono.error(new AuthException(
                    AuthException.ErrorType.UNAUTHORIZED,
                    AuthConstants.NO_TOKEN_PROVIDED_ERROR));
        }
        return Mono.just(token);
    }

    public static boolean hasAnyRole(Set<String> userRoles, Set<String> allowedRoles) {
        return allowedRoles.stream().anyMatch(userRoles::contains);
    }

}
