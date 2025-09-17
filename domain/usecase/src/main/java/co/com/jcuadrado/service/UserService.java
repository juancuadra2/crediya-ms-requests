package co.com.jcuadrado.service;

import co.com.jcuadrado.constant.AuthConstant;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.constant.RoleEnum;
import co.com.jcuadrado.constant.UserConstants;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    public static Mono<CreditRequest> validateAndSetInfo(CreditRequest creditRequest, UserRepository userRepository, AuthInfo authInfo) {
        return userRepository.getUserByDocumentNumber(creditRequest.getDocumentNumber(), authInfo.getToken())
                .switchIfEmpty(Mono.error(new BusinessException(UserConstants.USER_NOT_FOUND, ErrorCode.NOT_FOUND)))
                .flatMap(user -> {
                    if (authInfo.getRole().equals(RoleEnum.CLIENT.name()) && !Objects.equals(user.getEmail(), authInfo.getEmail())) {
                        return Mono.error(new BusinessException(AuthConstant.ACCESS_DENIED, ErrorCode.FORBIDDEN));
                    }
                    creditRequest.setEmail(user.getEmail());
                    return Mono.just(creditRequest);
                });
    }
}
