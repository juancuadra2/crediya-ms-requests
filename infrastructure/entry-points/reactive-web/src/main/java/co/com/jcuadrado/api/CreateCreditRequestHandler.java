package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.SuccessHttpStatus;
import co.com.jcuadrado.api.constant.auth.AuthRoles;
import co.com.jcuadrado.api.dto.request.CreateCreditRequestDTO;
import co.com.jcuadrado.api.services.AuthService;
import co.com.jcuadrado.api.services.BaseRequestService;
import co.com.jcuadrado.api.mapper.CreditRequestDTOMapper;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.usecase.creditrequest.CreateCreditRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateCreditRequestHandler {

    private final CreateCreditRequestUseCase createCreditRequestUseCase;
    private final CreditRequestDTOMapper creditRequestDTOMapper;
    private final BaseRequestService baseRequestService;
    private final AuthService authService;

    public Mono<ServerResponse> listenSaveRequest(ServerRequest serverRequest) {
        return authService.requireAnyRole(Set.of(AuthRoles.CLIENT.name()))
                .then(baseRequestService.extractAndValidateRequest(serverRequest, CreateCreditRequestDTO.class))
                .flatMap(tuple -> saveRequest(tuple.getT1(), tuple.getT2()));
    }

    private Mono<ServerResponse> saveRequest(CreateCreditRequestDTO dto, AuthInfo authInfo) {
        return createCreditRequestUseCase.saveCreditRequest(creditRequestDTOMapper.toModel(dto), authInfo)
                .transform(creditRequestDTOMapper::toDTOMono)
                .flatMap(creditRequestDTO ->
                        ResponseUtil.buildSuccessResponse(creditRequestDTO, SuccessHttpStatus.CREATED)
                );
    }
}
