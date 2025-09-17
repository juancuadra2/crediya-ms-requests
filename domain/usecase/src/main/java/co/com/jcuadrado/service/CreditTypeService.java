package co.com.jcuadrado.service;

import co.com.jcuadrado.constant.CreditRequestConstants;
import co.com.jcuadrado.constant.CreditTypeConstants;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.credittype.gateways.CreditTypeRepository;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CreditTypeService {

    public static Mono<CreditRequest> validateAndSetId(CreditRequest creditRequest, CreditTypeRepository creditTypeRepository) {
        return creditTypeRepository.getCreditTypeByName(creditRequest.getCreditType())
                .switchIfEmpty(Mono.error(new BusinessException(CreditTypeConstants.CREDIT_TYPE_NOT_FOUND, ErrorCode.NOT_FOUND)))
                .flatMap(creditType -> {
                    if (creditRequest.getAmount().compareTo(creditType.getMinAmount()) < 0) {
                        return Mono.error(new BusinessException(
                                String.format(CreditRequestConstants.AMOUNT_MIN_VALUE, creditType.getMinAmount()),
                                ErrorCode.BAD_REQUEST
                        ));
                    }

                    if (creditRequest.getAmount().compareTo(creditType.getMaxAmount()) > 0) {
                        return Mono.error(new BusinessException(
                                String.format(CreditRequestConstants.AMOUNT_MAX_VALUE, creditType.getMaxAmount()),
                                ErrorCode.BAD_REQUEST
                        ));
                    }
                    creditRequest.setCreditType(creditType.getId());
                    return Mono.just(creditRequest);
                });
    }
}
