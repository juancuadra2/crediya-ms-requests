package co.com.jcuadrado.handler;

import co.com.jcuadrado.constant.CreditRequestConstants;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CreateCreditRequestPayloadValidator {
    public static Mono<CreditRequest> validate(CreditRequest creditRequest){
        if (creditRequest == null) {
            return Mono.error(new BusinessException(CreditRequestConstants.CREDIT_REQUEST_NOT_NULL, ErrorCode.BAD_REQUEST));
        }
        if (creditRequest.getAmount() == null) {
            return Mono.error(new BusinessException(CreditRequestConstants.AMOUNT_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (creditRequest.getTerm() == null) {
            return Mono.error(new BusinessException(CreditRequestConstants.TERM_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (creditRequest.getDocumentNumber() == null) {
            return Mono.error(new BusinessException(CreditRequestConstants.DOCUMENT_NUMBER_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (creditRequest.getStatus() == null) {
            return Mono.error(new BusinessException(CreditRequestConstants.STATUS_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        if (creditRequest.getCreditType() == null) {
            return Mono.error(new BusinessException(CreditRequestConstants.CREDIT_TYPE_REQUIRED, ErrorCode.BAD_REQUEST));
        }
        return Mono.just(creditRequest);
    }
}
