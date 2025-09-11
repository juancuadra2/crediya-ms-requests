package co.com.jcuadrado.handler;

import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.usecase.creditstatus.CreditStatusUseCase;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CreditStatusValidator {

    public static Mono<CreditRequest> validateAndSetId(CreditRequest creditRequest, CreditStatusUseCase creditStatusUseCase) {
        return creditStatusUseCase.getCreditStatusByName(creditRequest.getStatus())
                .doOnNext(creditStatus -> creditRequest.setStatus(creditStatus.getId()))
                .thenReturn(creditRequest);
    }

}
