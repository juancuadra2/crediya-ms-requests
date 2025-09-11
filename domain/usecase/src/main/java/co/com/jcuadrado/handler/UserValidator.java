package co.com.jcuadrado.handler;

import co.com.jcuadrado.constant.AuthConstant;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.constant.UserConstants;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.auth.AuthResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.usecase.user.UserUseCase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidator {

    public static Mono<CreditRequest> validateAndSetEmail(CreditRequest creditRequest, UserUseCase userUseCase, AuthResponse authResponse) {
        return userUseCase.getUserByDocumentNumber(creditRequest.getDocumentNumber(), authResponse.getToken())
                .flatMap(user -> {
                    if (authResponse.getRole().equals(UserConstants.ROLE_CLIENT) && !Objects.equals(user.getEmail(), authResponse.getEmail())) {
                        return Mono.error(new BusinessException(AuthConstant.ACCESS_DENIED, ErrorCode.FORBIDDEN));
                    }
                    creditRequest.setEmail(user.getEmail());
                    return Mono.just(creditRequest);
                });
    }
}
