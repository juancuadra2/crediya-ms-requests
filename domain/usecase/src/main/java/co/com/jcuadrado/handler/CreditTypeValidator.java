package co.com.jcuadrado.handler;

import co.com.jcuadrado.constant.CreditRequestConstants;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.usecase.credittype.CreditTypeUseCase;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CreditTypeValidator {

    public static Mono<CreditRequest> validateAndSetId(CreditRequest creditRequest, CreditTypeUseCase creditTypeUseCase) {
        return creditTypeUseCase.getCreditTypeByName(creditRequest.getCreditType())
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
