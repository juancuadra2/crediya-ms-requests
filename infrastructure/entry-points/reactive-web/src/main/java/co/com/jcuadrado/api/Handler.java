package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.HttpStatusConstants;
import co.com.jcuadrado.api.constant.auth.AuthRoles;
import co.com.jcuadrado.api.dto.request.CreateCreditRequestDTO;
import co.com.jcuadrado.api.dto.request.CreditRequestFilterDTO;
import co.com.jcuadrado.api.mapper.CreditRequestDTOMapper;
import co.com.jcuadrado.api.mapper.CreditRequestFilterDTOMapper;
import co.com.jcuadrado.api.mapper.PageResponseDTOMapper;
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
import reactor.util.function.Tuple2;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Handler {

    private final CreditRequestUseCase creditRequestUseCase;
    private final TokenManagerUseCase tokenManagerUseCase;
    private final CreditRequestListUseCase creditRequestListUseCase;
    private final CreditRequestFilterDTOMapper creditRequestFilterDTOMapper;
    private final Validator validator;
    private final CreditRequestDTOMapper creditRequestDTOMapper;
    private final PageResponseDTOMapper pageResponseDTOMapper;

    public Mono<ServerResponse> listenSaveRequest(ServerRequest serverRequest) {
        return AuthorizationUtil.requireAnyRole(Set.of(AuthRoles.CLIENT.name()))
                .then(extractAndValidateRequest(serverRequest))
                .flatMap(tuple -> saveRequest(tuple.getT1(), tuple.getT2()));
    }


    public Mono<ServerResponse> listenGetRequests(ServerRequest serverRequest) {
        CreditRequestFilterDTO creditRequestFilterDTO = QueryParamUtil.getParams(serverRequest, CreditRequestFilterDTO.class);
        return AuthorizationUtil.requireAnyRole(Set.of(AuthRoles.ADVISER.name()))
                .then(AuthInfoUtil.getAuthInfo(serverRequest, tokenManagerUseCase))
                .flatMap(authInfo -> creditRequestListUseCase
                        .getCreditRequests(creditRequestFilterDTOMapper.toModel(creditRequestFilterDTO), authInfo)
                        .transform(pageResponseDTOMapper::toDTOMono)
                )
                .flatMap(pageResponse -> ResponseUtil.buildSuccessResponse(pageResponse, HttpStatusConstants.OK));
    }

    private Mono<Tuple2<CreateCreditRequestDTO, AuthInfo>> extractAndValidateRequest(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateCreditRequestDTO.class)
                .zipWith(AuthInfoUtil.getAuthInfo(serverRequest, tokenManagerUseCase))
                .flatMap(tuple -> {
                    CreateCreditRequestDTO dto = tuple.getT1();
                    return ValidationUtil.validateOrThrow(validator, dto)
                            .thenReturn(tuple);
                });
    }

    private Mono<ServerResponse> saveRequest(CreateCreditRequestDTO dto, AuthInfo authInfo) {
        return creditRequestUseCase.saveCreditRequest(creditRequestDTOMapper.toModel(dto), authInfo)
                .transform(creditRequestDTOMapper::toDTOMono)
                .flatMap(creditRequestDTO ->
                        ResponseUtil.buildSuccessResponse(creditRequestDTO, HttpStatusConstants.CREATED)
                );
    }

}
