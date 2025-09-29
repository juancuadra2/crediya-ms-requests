package co.com.jcuadrado.usecase.auth;

import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.constant.AuthConstant;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.auth.gateways.AuthTokenGateway;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record TokenManagerUseCase(AuthTokenGateway authTokenGateway) {

    public Mono<Boolean> validateToken(String token) {
        return validateInput(token)
                .then(Mono.defer(() -> authTokenGateway.validateToken(token)));
    }

    public Mono<String> getSubject(String token) {
        return validateInput(token)
                .then(Mono.defer(() -> authTokenGateway.getSubject(token)));
    }

    public Flux<String> getRoles(String token) {
        return validateInput(token)
                .thenMany(Flux.defer(() -> authTokenGateway.getRoles(token)));
    }

    private Mono<Void> validateInput(String token){
        if (token == null) return Mono.error(new BusinessException(AuthConstant.INVALID_TOKEN, ErrorCode.BAD_REQUEST));
        return Mono.empty();
    }

}