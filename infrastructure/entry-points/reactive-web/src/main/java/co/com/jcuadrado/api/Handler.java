package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.HttpStatusConstants;
import co.com.jcuadrado.api.dto.request.CreateCreditRequestDTO;
import co.com.jcuadrado.api.mapper.CreditRequestDTOMapper;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.api.util.TokenUtil;
import co.com.jcuadrado.api.util.ValidationUtil;
import co.com.jcuadrado.model.auth.AuthResponse;
import co.com.jcuadrado.usecase.auth.TokenManagerUseCase;
import co.com.jcuadrado.usecase.creditrequest.CreditRequestUseCase;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final CreditRequestUseCase creditRequestUseCase;
    private final TokenManagerUseCase tokenManagerUseCase;
    private final Validator validator;
    private final CreditRequestDTOMapper creditRequestDTOMapper;

    public Mono<ServerResponse> listenSaveRequest(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateCreditRequestDTO.class)
                .zipWith(getAuthInfo(serverRequest))
                .flatMap(tuple -> {
                    CreateCreditRequestDTO createCreditRequestDTO = tuple.getT1();
                    AuthResponse authResponse = tuple.getT2();
                    return ValidationUtil.validateOrThrow(validator, createCreditRequestDTO)
                            .then(Mono.defer(() -> creditRequestUseCase.saveCreditRequest(creditRequestDTOMapper.toModel(createCreditRequestDTO), authResponse)
                                    .transform(creditRequestDTOMapper::toDTOMono)
                                    .flatMap(creditRequestDTO ->
                                            ResponseUtil.buildSuccessResponse(creditRequestDTO, HttpStatusConstants.CREATED)
                                    ))
                            );
                });
    }

    private Mono<AuthResponse> getAuthInfo(ServerRequest serverRequest) {
        return TokenUtil.extractToken(serverRequest)
                .flatMap(token -> Mono.zip(
                        Mono.just(token), tokenManagerUseCase.getSubject(token), tokenManagerUseCase.getRoles(token).next())
                )
                .map(tuple -> AuthResponse.builder()
                        .token(tuple.getT1())
                        .email(tuple.getT2())
                        .role(tuple.getT3())
                        .build()
                );
    }

}
