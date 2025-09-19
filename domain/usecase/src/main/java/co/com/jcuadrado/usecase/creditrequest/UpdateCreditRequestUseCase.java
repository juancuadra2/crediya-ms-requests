package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.constant.AuthConstant;
import co.com.jcuadrado.constant.CreditRequestConstants;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.constant.RoleEnum;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import co.com.jcuadrado.model.creditrequest.gateways.NotifyCreditRequest;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import co.com.jcuadrado.service.CreditStatusService;
import co.com.jcuadrado.validator.UpdateCreditRequestPayloadValidator;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import reactor.core.publisher.Mono;

public record UpdateCreditRequestUseCase(
        CreditRequestRepository creditRequestRepository,
        CreditStatusRepository creditStatusRepository,
        NotifyCreditRequest notifyCreditRequest
) {

    public Mono<CreditRequestResponse> changeStatus(String id, String status, AuthInfo authInfo) {
        if (!authInfo.getRole().equals(RoleEnum.ADVISER.name())) {
            return Mono.error(new BusinessException(AuthConstant.ACCESS_DENIED, ErrorCode.FORBIDDEN));
        }
        CreditRequest creditRequest = CreditRequest.builder().id(id).status(status).build();
        return UpdateCreditRequestPayloadValidator.validateInput(creditRequest)
                .flatMap(creditRequestValidated -> CreditStatusService.validateAndSetId(creditRequest, creditStatusRepository))
                .flatMap(creditRequestRepository::updateCreditRequestStatus)
                .switchIfEmpty(Mono.error(new BusinessException(CreditRequestConstants.CREDIT_REQUEST_NOT_FOUND, ErrorCode.NOT_FOUND)))
                .flatMap(this::notifyCreditRequest);
    }

    private Mono<CreditRequestResponse> notifyCreditRequest(CreditRequestResponse creditRequestResponse) {
        return notifyCreditRequest.notify(creditRequestResponse).thenReturn(creditRequestResponse);
    }

}
