package co.com.jcuadrado.model.creditrequest.gateways;

import co.com.jcuadrado.model.creditrequest.CreditRequest;
import reactor.core.publisher.Mono;

public interface CreditRequestRepository {
    Mono<CreditRequest> saveCreditRequest(CreditRequest creditRequest);
}
