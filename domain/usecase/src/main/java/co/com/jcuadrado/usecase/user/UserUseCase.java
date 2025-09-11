package co.com.jcuadrado.usecase.user;

import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.constant.UserConstants;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public record UserUseCase(UserRepository userRepository) {

    public Mono<User> getUserByDocumentNumber(String documentNumber, String token) {
        return userRepository.getUserByDocumentNumber(documentNumber, token)
                .switchIfEmpty(Mono.error(new BusinessException(UserConstants.USER_NOT_FOUND, ErrorCode.NOT_FOUND)));
    }
}
