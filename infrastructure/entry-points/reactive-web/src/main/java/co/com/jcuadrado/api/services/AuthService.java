package co.com.jcuadrado.api.services;

import co.com.jcuadrado.api.constant.auth.AuthConstants;
import co.com.jcuadrado.api.exception.AuthException;
import co.com.jcuadrado.api.util.AuthUtil;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.usecase.auth.TokenManagerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final TokenManagerUseCase tokenManagerUseCase;

    public Mono<Boolean> requireAnyRole(Set<String> allowedRoles) {
        return getCurrentUserRoles()
                .switchIfEmpty(Mono.error(new AuthException(AuthException.ErrorType.FORBIDDEN, AuthConstants.USER_NOT_AUTHENTICATED_ERROR)))
                .flatMap(currentUserRoles -> {
                    boolean hasRequiredRole = AuthUtil.hasAnyRole(currentUserRoles, allowedRoles);

                    if (hasRequiredRole) {
                        return Mono.just(true);
                    }
                    return Mono.error(new AuthException(AuthException.ErrorType.FORBIDDEN, AuthConstants.ACCESS_DENIED_ERROR));
                });
    }

    public Mono<AuthInfo> getAuthInfo(ServerRequest serverRequest) {
        return AuthUtil.extractToken(serverRequest)
                .flatMap(token -> Mono.zip(
                        Mono.just(token),
                        tokenManagerUseCase.getSubject(token),
                        tokenManagerUseCase.getRoles(token).next())
                )
                .map(tuple -> AuthInfo.builder()
                        .token(tuple.getT1())
                        .email(tuple.getT2())
                        .role(tuple.getT3())
                        .build());
    }

    private Mono<Set<String>> getCurrentUserRoles() {
        return getAuthentication()
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()));
    }

    private Mono<Authentication> getAuthentication() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication);
    }
}
