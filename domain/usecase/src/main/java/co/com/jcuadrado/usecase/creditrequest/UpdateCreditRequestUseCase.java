package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.handler.CreditStatusValidator;
import co.com.jcuadrado.handler.UpdateCreditRequestPayloadValidator;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.usecase.creditstatus.CreditStatusUseCase;
import reactor.core.publisher.Mono;

public record UpdateCreditRequestUseCase(
        CreditRequestRepository creditRequestRepository,
        CreditStatusUseCase creditStatusUseCase
) {

    public Mono<CreditRequest> changeStatus(String id, String status, AuthInfo authInfo) {
        CreditRequest creditRequest = CreditRequest.builder().id(id).status(status).build();
        return UpdateCreditRequestPayloadValidator.validateInput(creditRequest)
                .flatMap(creditRequestValidated -> CreditStatusValidator.validateAndSetId(creditRequest, creditStatusUseCase))
                .flatMap(creditRequestRepository::updateCreditRequestStatus);
    }

}
