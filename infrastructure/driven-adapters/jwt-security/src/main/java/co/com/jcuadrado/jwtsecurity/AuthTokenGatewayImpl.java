package co.com.jcuadrado.jwtsecurity;

import co.com.jcuadrado.jwtsecurity.constant.JwtConstants;
import co.com.jcuadrado.model.auth.gateways.AuthTokenGateway;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthTokenGatewayImpl implements AuthTokenGateway {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Boolean> validateToken(String token) {
        return Mono.fromCallable(() -> jwtProvider.validate(token));
    }

    @Override
    public Mono<String> getSubject(String token) {
        return Mono.fromCallable(() -> jwtProvider.getSubject(token));
    }

    @Override
    public Flux<String> getRoles(String token) {
        Claims claims = jwtProvider.getClaims(token);
        Object rolesObj = claims.get(JwtConstants.ROLES_CLAIM);
        if (rolesObj instanceof Iterable<?> roles) {
            return Flux.fromIterable(roles)
                    .mapNotNull(roleObj -> {
                        if (roleObj instanceof java.util.Map<?, ?> roleMap) {
                            Object authority = roleMap.get(JwtConstants.AUTHORITIES_CLAIM);
                            return authority != null ? authority.toString() : null;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull);
        }
        return Flux.empty();
    }
}
