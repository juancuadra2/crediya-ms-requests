package co.com.jcuadrado.validator;

import co.com.jcuadrado.constant.CreditRequestConstants;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UpdateCreditRequestPayloadValidator {
    public static Mono<CreditRequest> validateInput(CreditRequest creditRequest) {
        if (creditRequest.getId() == null || creditRequest.getId().isEmpty()) {
            return Mono.error(new BusinessException(CreditRequestConstants.ID_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (creditRequest.getStatus() == null || creditRequest.getStatus().isEmpty()) {
            return Mono.error(new BusinessException(CreditRequestConstants.STATUS_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        return Mono.just(creditRequest);
    }
}
