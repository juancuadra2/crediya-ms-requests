package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.HttpStatusConstants;
import co.com.jcuadrado.api.constant.auth.AuthRoles;
import co.com.jcuadrado.api.dto.request.CreateCreditRequestDTO;
import co.com.jcuadrado.api.dto.request.CreditRequestFilterDTO;
import co.com.jcuadrado.api.mapper.CreditRequestDTOMapper;
import co.com.jcuadrado.api.mapper.CreditRequestFilterDTOMapper;
import co.com.jcuadrado.api.util.*;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.usecase.auth.TokenManagerUseCase;
import co.com.jcuadrado.usecase.creditrequest.CreditRequestListUseCase;
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
    private final CreditRequestListUseCase creditRequestListUseCase;
    private final CreditRequestFilterDTOMapper creditRequestFilterDTOMapper;
    private final Validator validator;
    private final CreditRequestDTOMapper creditRequestDTOMapper;

    public Mono<ServerResponse> listenSaveRequest(ServerRequest serverRequest) {
        return AuthorizationUtil.requireAnyRole(new String[]{AuthRoles.CLIENT.name()},
                serverRequest.bodyToMono(CreateCreditRequestDTO.class)
                        .zipWith(getAuthInfo(serverRequest))
                        .flatMap(tuple -> {
                            CreateCreditRequestDTO createCreditRequestDTO = tuple.getT1();
                            AuthInfo authInfo = tuple.getT2();
                            return ValidationUtil.validateOrThrow(validator, createCreditRequestDTO)
                                    .then(Mono.defer(() -> creditRequestUseCase.saveCreditRequest(creditRequestDTOMapper.toModel(createCreditRequestDTO), authInfo)
                                            .transform(creditRequestDTOMapper::toDTOMono)
                                            .flatMap(creditRequestDTO ->
                                                    ResponseUtil.buildSuccessResponse(creditRequestDTO, HttpStatusConstants.CREATED)
                                            ))
                                    );
                        }));
    }

    public Mono<ServerResponse> listenGetRequests(ServerRequest serverRequest) {
        CreditRequestFilterDTO creditRequestFilterDTO = QueryParamUtil.getParams(serverRequest, CreditRequestFilterDTO.class);
        return getAuthInfo(serverRequest)
                .flatMap(authInfo ->
                        creditRequestListUseCase.getCreditRequests(creditRequestFilterDTOMapper.toModel(creditRequestFilterDTO), authInfo)
                )
                .flatMap(pageResponse -> ResponseUtil.buildSuccessResponse(pageResponse, HttpStatusConstants.OK));
    }

    private Mono<AuthInfo> getAuthInfo(ServerRequest serverRequest) {
        return TokenUtil.extractToken(serverRequest)
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
