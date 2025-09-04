package co.com.jcuadrado.handler;

import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.usecase.user.UserUseCase;
import reactor.core.publisher.Mono;

public record UserValidator() {

    public static Mono<CreditRequest> validateAndSetEmail(CreditRequest creditRequest, UserUseCase userUseCase) {
        return userUseCase.getUserByDocumentNumber(creditRequest.getDocumentNumber())
                .doOnNext(user -> creditRequest.setEmail(user.getEmail()))
                .thenReturn(creditRequest);
    }
}
