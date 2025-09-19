package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.SuccessHttpStatus;
import co.com.jcuadrado.api.constant.auth.AuthRoles;
import co.com.jcuadrado.api.dto.request.CreditRequestFilterDTO;
import co.com.jcuadrado.api.mapper.PageResponseDtoMapper;
import co.com.jcuadrado.api.services.AuthService;
import co.com.jcuadrado.api.mapper.CreditRequestFilterDtoMapper;
import co.com.jcuadrado.api.util.QueryParamUtil;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.usecase.creditrequest.GetCreditRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class GetCreditRequestHandler {

    private final GetCreditRequestUseCase getCreditRequestUseCase;
    private final CreditRequestFilterDtoMapper creditRequestFilterDTOMapper;
    private final PageResponseDtoMapper pageResponseDTOMapper;
    private final AuthService authService;

    public Mono<ServerResponse> listenGetRequests(ServerRequest serverRequest) {
        CreditRequestFilterDTO creditRequestFilterDTO = QueryParamUtil.getParams(serverRequest, CreditRequestFilterDTO.class);
        return authService.requireAnyRole(Set.of(AuthRoles.ADVISER.name()))
                .then(authService.getAuthInfo(serverRequest))
                .flatMap(authInfo -> getCreditRequestUseCase
                        .getCreditRequests(creditRequestFilterDTOMapper.toModel(creditRequestFilterDTO), authInfo)
                        .transform(pageResponseDTOMapper::toDTOMono)
                )
                .flatMap(pageResponse -> ResponseUtil.buildSuccessResponse(pageResponse, SuccessHttpStatus.OK));
    }

}
