package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import co.com.jcuadrado.service.CreditStatusService;
import co.com.jcuadrado.validator.UpdateCreditRequestPayloadValidator;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import reactor.core.publisher.Mono;

public record UpdateCreditRequestUseCase(
        CreditRequestRepository creditRequestRepository,
        CreditStatusRepository creditStatusRepository
) {

    public Mono<CreditRequest> changeStatus(String id, String status, AuthInfo authInfo) {
        CreditRequest creditRequest = CreditRequest.builder().id(id).status(status).build();
        return UpdateCreditRequestPayloadValidator.validateInput(creditRequest)
                .flatMap(creditRequestValidated -> CreditStatusService.validateAndSetId(creditRequest, creditStatusRepository))
                .flatMap(creditRequestRepository::updateCreditRequestStatus);
    }

}
