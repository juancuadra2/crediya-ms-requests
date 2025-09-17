package co.com.jcuadrado.service;

import co.com.jcuadrado.constant.CreditStatusConstants;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CreditStatusService {

    public static Mono<CreditRequest> validateAndSetId(
            CreditRequest creditRequest,
            CreditStatusRepository creditStatusRepository
    ) {
        return creditStatusRepository.getCreditStatusByName(creditRequest.getStatus())
                .switchIfEmpty(Mono.error(new BusinessException(CreditStatusConstants.CREDIT_STATUS_NOT_FOUND, ErrorCode.NOT_FOUND)))
                .doOnNext(creditStatus -> creditRequest.setStatus(creditStatus.getId()))
                .thenReturn(creditRequest);
    }

}
