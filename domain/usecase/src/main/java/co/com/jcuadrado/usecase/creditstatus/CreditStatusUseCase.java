package co.com.jcuadrado.usecase.creditstatus;

import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.creditstatus.CreditStatus;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import co.com.jcuadrado.constant.CreditStatusConstants;
import reactor.core.publisher.Mono;

public record CreditStatusUseCase(CreditStatusRepository creditStatusRepository) {

    public Mono<CreditStatus> getCreditStatusByName(String name) {
        return creditStatusRepository.getCreditStatusByName(name)
                .switchIfEmpty(Mono.error(
                        new BusinessException(CreditStatusConstants.CREDIT_STATUS_NOT_FOUND, ErrorCode.NOT_FOUND)));
    }
}
