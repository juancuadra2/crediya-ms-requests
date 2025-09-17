package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.handler.*;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.usecase.creditstatus.CreditStatusUseCase;
import co.com.jcuadrado.usecase.credittype.CreditTypeUseCase;
import co.com.jcuadrado.usecase.user.UserUseCase;
import reactor.core.publisher.Mono;

public record CreateCreditRequestUseCase(
        CreditRequestRepository creditRequestRepository,
        CreditStatusUseCase creditStatusUseCase, CreditTypeUseCase creditTypeUseCase,
        UserUseCase userUseCase) {

    public Mono<CreditRequest> saveCreditRequest(CreditRequest creditRequest, AuthInfo authInfo) {
        creditRequest.setStatus(CreditStatusEnum.PENDING.name());
        return validateCreditRequest(creditRequest, authInfo)
                .flatMap(creditRequestRepository::saveCreditRequest);
    }

    private Mono<CreditRequest> validateCreditRequest(CreditRequest creditRequest, AuthInfo authInfo) {
        return CreateCreditRequestPayloadValidator.validate(creditRequest)
                .flatMap(cr1 -> UserValidator.validateAndSetInfo(creditRequest, userUseCase, authInfo))
                .flatMap(cr2 -> CreditStatusValidator.validateAndSetId(creditRequest, creditStatusUseCase))
                .flatMap(cr3 -> CreditTypeValidator.validateAndSetId(creditRequest, creditTypeUseCase));
    }

}
