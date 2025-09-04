package co.com.jcuadrado.usecase.credittype;

import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.credittype.CreditType;
import co.com.jcuadrado.model.credittype.gateways.CreditTypeRepository;
import co.com.jcuadrado.constant.CreditTypeConstants;
import reactor.core.publisher.Mono;

public record CreditTypeUseCase(CreditTypeRepository creditTypeRepository) {

    public Mono<CreditType> getCreditTypeByName(String name) {
        return creditTypeRepository.getCreditTypeByName(name)
                .switchIfEmpty(Mono
                        .error(new BusinessException(CreditTypeConstants.CREDIT_TYPE_NOT_FOUND, ErrorCode.NOT_FOUND)));
    }
}
