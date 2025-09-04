package co.com.jcuadrado.model.user.gateways;

import co.com.jcuadrado.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> getUserByDocumentNumber(String documentNumber);
}
