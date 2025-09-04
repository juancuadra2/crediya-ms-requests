package co.com.jcuadrado.api;

import co.com.jcuadrado.api.constant.SuccessStatus;
import co.com.jcuadrado.api.dto.request.CreateCreditRequestDTO;
import co.com.jcuadrado.api.mapper.CreditRequestDTOMapper;
import co.com.jcuadrado.api.util.ResponseUtil;
import co.com.jcuadrado.api.util.ValidationUtil;
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

    private final CreditRequestUseCase useCase;
    private final Validator validator;
    private final CreditRequestDTOMapper creditRequestDTOMapper;

    public Mono<ServerResponse> listenSaveRequest(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateCreditRequestDTO.class)
                .flatMap(createCreditRequestDTO -> ValidationUtil.validateAndReturnError(validator, createCreditRequestDTO)
                        .switchIfEmpty(useCase.saveCreditRequest(creditRequestDTOMapper.toModel(createCreditRequestDTO))
                                .transform(creditRequestDTOMapper::toDTOMono)
                                .flatMap(creditRequestDTO -> ResponseUtil.buildSuccessResponse(creditRequestDTO, SuccessStatus.CREATED))));
                     
    }
}
