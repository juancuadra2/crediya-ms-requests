package co.com.jcuadrado.model.auth.gateways;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthTokenGateway {
    Mono<Boolean> validateToken(String token);
    Mono<String> getSubject(String token);
    Flux<String> getRoles(String token);
}
