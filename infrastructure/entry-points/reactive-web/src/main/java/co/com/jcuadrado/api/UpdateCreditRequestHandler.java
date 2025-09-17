package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.api.SuccessHttpStatus;
import co.com.jcuadrado.api.constant.auth.AuthRoles;
import co.com.jcuadrado.api.constant.validation.CreditRequestValidationConstants;
import co.com.jcuadrado.api.dto.request.ChangeStatusCreditRequestDto;
import co.com.jcuadrado.api.services.AuthService;
import co.com.jcuadrado.api.services.BaseRequestService;
import co.com.jcuadrado.api.mapper.CreditRequestDTOMapper;
import co.com.jcuadrado.api.util.*;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.usecase.creditrequest.UpdateCreditRequestUseCase;

import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UpdateCreditRequestHandler {

    private final UpdateCreditRequestUseCase updateCreditRequestUseCase;
    private final CreditRequestDTOMapper creditRequestDTOMapper;
    private final BaseRequestService baseRequestService;
    private final AuthService authService;

    public Mono<ServerResponse> listenChangeStatusRequest(ServerRequest serverRequest) {
        String requestId = serverRequest.pathVariable("id");
        if (requestId.isBlank()) return Mono.error(new ValidationException(CreditRequestValidationConstants.ID_REQUIRED_MESSAGE));

        return authService.requireAnyRole(Set.of(AuthRoles.ADVISER.name()))
                .then(baseRequestService.extractAndValidateRequest(serverRequest, ChangeStatusCreditRequestDto.class))
                .flatMap(tuple -> {
                    AuthInfo authInfo = tuple.getT2();
                    String status = tuple.getT1().status();
                    return updateCreditRequestUseCase.changeStatus(requestId, status, authInfo).transform(creditRequestDTOMapper::toDTOMono);
                })
                .flatMap(creditRequestDTO -> ResponseUtil.buildSuccessResponse(creditRequestDTO, SuccessHttpStatus.OK));
    }

}
