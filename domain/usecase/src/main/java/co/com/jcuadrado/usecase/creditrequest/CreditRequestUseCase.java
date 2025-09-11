package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.handler.*;
import co.com.jcuadrado.model.auth.AuthResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.usecase.creditstatus.CreditStatusUseCase;
import co.com.jcuadrado.usecase.credittype.CreditTypeUseCase;
import co.com.jcuadrado.usecase.user.UserUseCase;
import reactor.core.publisher.Mono;

public record CreditRequestUseCase(
        CreditRequestRepository creditRequestRepository,
        CreditStatusUseCase creditStatusUseCase, CreditTypeUseCase creditTypeUseCase,
        UserUseCase userUseCase) {

    public Mono<CreditRequest> saveCreditRequest(CreditRequest creditRequest, AuthResponse authResponse) {
        //TODO: Implmentar validacion de que si es CLIENT no puede crear solicitudes de otro usuario
        creditRequest.setStatus(CreditStatusEnum.PENDING.getDescription());
        return validateCreditRequest(creditRequest)
                .flatMap(creditRequestRepository::saveCreditRequest);
    }

    private Mono<CreditRequest> validateCreditRequest(CreditRequest creditRequest) {
        return PayloadValidator.validate(creditRequest)
                .flatMap(cr1 -> UserValidator.validateAndSetEmail(creditRequest, userUseCase))
                .flatMap(cr2 -> CreditStatusValidator.validateAndSetId(creditRequest, creditStatusUseCase))
                .flatMap(cr3 -> CreditTypeValidator.validateAndSetId(creditRequest, creditTypeUseCase));
    }

}
