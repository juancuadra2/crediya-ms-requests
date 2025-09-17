package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.validator.*;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import co.com.jcuadrado.model.credittype.gateways.CreditTypeRepository;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import co.com.jcuadrado.service.CreditStatusService;
import co.com.jcuadrado.service.CreditTypeService;
import co.com.jcuadrado.service.UserService;
import reactor.core.publisher.Mono;

public record CreateCreditRequestUseCase(
        CreditRequestRepository creditRequestRepository,
        CreditStatusRepository creditStatusRepository,
        CreditTypeRepository creditTypeRepository,
        UserRepository userRepository
) {

    public Mono<CreditRequest> saveCreditRequest(CreditRequest creditRequest, AuthInfo authInfo) {
        creditRequest.setStatus(CreditStatusEnum.PENDING.name());
        return validateCreditRequest(creditRequest, authInfo)
                .flatMap(creditRequestRepository::saveCreditRequest);
    }

    private Mono<CreditRequest> validateCreditRequest(CreditRequest creditRequest, AuthInfo authInfo) {
        return CreateCreditRequestPayloadValidator.validate(creditRequest)
                .flatMap(cr1 -> UserService.validateAndSetInfo(creditRequest, userRepository, authInfo))
                .flatMap(cr2 -> CreditStatusService.validateAndSetId(creditRequest, creditStatusRepository))
                .flatMap(cr3 -> CreditTypeService.validateAndSetId(creditRequest, creditTypeRepository));
    }

}
