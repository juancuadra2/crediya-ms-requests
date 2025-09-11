package co.com.jcuadrado.handler;

import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.usecase.user.UserUseCase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidator {

    public static Mono<CreditRequest> validateAndSetEmail(CreditRequest creditRequest, UserUseCase userUseCase) {
        return userUseCase.getUserByDocumentNumber(creditRequest.getDocumentNumber())
                .doOnNext(user -> creditRequest.setEmail(user.getEmail()))
                .thenReturn(creditRequest);
    }
}
