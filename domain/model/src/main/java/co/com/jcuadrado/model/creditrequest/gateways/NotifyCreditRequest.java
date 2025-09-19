package co.com.jcuadrado.model.creditrequest.gateways;

import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import reactor.core.publisher.Mono;

public interface NotifyCreditRequest {
    Mono<String> notify(CreditRequestResponse creditRequestResponse);
}
