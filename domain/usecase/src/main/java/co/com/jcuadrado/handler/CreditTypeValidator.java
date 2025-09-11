package co.com.jcuadrado.handler;

import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.usecase.credittype.CreditTypeUseCase;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CreditTypeValidator {

    public static Mono<CreditRequest> validateAndSetId(CreditRequest creditRequest, CreditTypeUseCase creditTypeUseCase) {
        return creditTypeUseCase.getCreditTypeByName(creditRequest.getCreditType())
                .doOnNext(creditType -> creditRequest.setCreditType(creditType.getId()))
                .thenReturn(creditRequest);
    }
}
