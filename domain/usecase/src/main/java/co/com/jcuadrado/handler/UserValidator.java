package co.com.jcuadrado.handler;

import co.com.jcuadrado.constant.AuthConstant;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.constant.RoleEnum;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.usecase.user.UserUseCase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidator {

    public static Mono<CreditRequest> validateAndSetInfo(CreditRequest creditRequest, UserUseCase userUseCase, AuthInfo authInfo) {
        return userUseCase.getUserByDocumentNumber(creditRequest.getDocumentNumber(), authInfo.getToken())
                .flatMap(user -> {
                    if (authInfo.getRole().equals(RoleEnum.CLIENT.name()) && !Objects.equals(user.getEmail(), authInfo.getEmail())) {
                        return Mono.error(new BusinessException(AuthConstant.ACCESS_DENIED, ErrorCode.FORBIDDEN));
                    }
                    creditRequest.setEmail(user.getEmail());
                    return Mono.just(creditRequest);
                });
    }
}
