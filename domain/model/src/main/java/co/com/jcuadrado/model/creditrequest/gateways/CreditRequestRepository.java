package co.com.jcuadrado.model.creditrequest.gateways;

import co.com.jcuadrado.model.common.PageResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.CreditRequestFilter;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import reactor.core.publisher.Mono;

public interface CreditRequestRepository {
    Mono<CreditRequest> saveCreditRequest(CreditRequest creditRequest);
    Mono<PageResponse<CreditRequestResponse>> findCreditRequests(CreditRequestFilter filter);
    Mono<CreditRequestResponse> updateCreditRequestStatus(CreditRequest creditRequest);
}
