package co.com.jcuadrado.consumer;

import co.com.jcuadrado.consumer.constants.UserServiceClientConstants;
import co.com.jcuadrado.consumer.exceptions.RestConsumerException;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.model.user.gateways.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceClient implements UserRepository {

    private final WebClient client;

    @Override
    @CircuitBreaker(name = UserServiceClientConstants.USER_SERVICE_CIRCUIT_BREAKER_NAME)
    public Mono<User> getUserByDocumentNumber(String documentNumber, String token) {
        return client
                .get()
                .uri(UserServiceClientConstants.USER_BY_DOCUMENT_NUMBER_URI, documentNumber)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorResume(WebClientResponseException.NotFound.class,
                        ex -> Mono.empty())
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        ex -> new RestConsumerException(RestConsumerException.ErrorType.BAD_REQUEST,
                                UserServiceClientConstants.BAD_REQUEST_ERROR_MESSAGE, ex))
                .onErrorMap(Exception.class,
                        ex -> new RestConsumerException(RestConsumerException.ErrorType.UNPROCESSABLE,
                                UserServiceClientConstants.CONNECTION_ERROR_MESSAGE, ex));
    }

}
