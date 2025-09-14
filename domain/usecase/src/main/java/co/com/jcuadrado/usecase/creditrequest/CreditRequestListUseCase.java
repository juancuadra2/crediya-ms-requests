package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.common.PageResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequestFilter;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import reactor.core.publisher.Mono;

public record CreditRequestListUseCase(CreditRequestRepository creditRequestRepository) {

    public Mono<PageResponse<CreditRequestResponse>> getCreditRequests(CreditRequestFilter filter, AuthInfo authInfo) {
        return creditRequestRepository.findCreditRequests(filter);
    }

}