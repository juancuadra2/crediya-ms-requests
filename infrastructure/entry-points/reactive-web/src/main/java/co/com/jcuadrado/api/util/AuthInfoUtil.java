package co.com.jcuadrado.api.util;

import co.com.jcuadrado.api.constant.auth.AuthConstants;
import co.com.jcuadrado.api.exception.AuthException;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.usecase.auth.TokenManagerUseCase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthInfoUtil {

    public static Mono<String> extractToken(ServerRequest request) {
        String token = request.exchange().getAttribute(AuthConstants.TOKEN_ATTRIBUTE);

        if (token == null) {
            return Mono.error(new AuthException(AuthException.ErrorType.UNAUTHORIZED, AuthConstants.NO_TOKEN_PROVIDED_ERROR));
        }
        return Mono.just(token);
    }

    public static Mono<AuthInfo> getAuthInfo(ServerRequest serverRequest, TokenManagerUseCase tokenManagerUseCase) {
        return extractToken(serverRequest)
                .flatMap(token -> Mono.zip(
                        Mono.just(token), tokenManagerUseCase.getSubject(token), tokenManagerUseCase.getRoles(token).next())
                )
                .map(tuple -> AuthInfo.builder()
                        .token(tuple.getT1())
                        .email(tuple.getT2())
                        .role(tuple.getT3())
                        .build()
                );
    }

}
