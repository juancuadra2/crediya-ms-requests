package co.com.jcuadrado.api.services;

import co.com.jcuadrado.model.auth.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
@RequiredArgsConstructor
public class BaseRequestService {

    private final AuthService authService;
    private final ValidationService validationService;

    public <T> Mono<Tuple2<T, AuthInfo>> extractAndValidateRequest(ServerRequest serverRequest, Class<T> clazz) {
        return serverRequest.bodyToMono(clazz)
                .zipWith(authService.getAuthInfo(serverRequest))
                .flatMap(tuple -> {
                    T dto = tuple.getT1();
                    return validationService.validateOrThrow(dto).thenReturn(tuple);
                });
    }

}
