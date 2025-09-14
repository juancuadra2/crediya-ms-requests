package co.com.jcuadrado.r2dbc.repository.request;

import co.com.jcuadrado.model.common.PageResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.CreditRequestFilter;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.r2dbc.entity.RequestEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class RequestReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        CreditRequest,
        RequestEntity,
        UUID,
        RequestReactiveRepository
        > implements CreditRequestRepository {

    protected RequestReactiveRepositoryAdapter(RequestReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, CreditRequest.class));
    }

    @Override
    public Mono<CreditRequest> saveCreditRequest(CreditRequest creditRequest) {
        RequestEntity requestEntity = toData(creditRequest);
        requestEntity.setStatusId(UUID.fromString(creditRequest.getStatus()));
        requestEntity.setTypeId(UUID.fromString(creditRequest.getCreditType()));
        return repository.save(requestEntity)
                .map(this::toEntity)
                .doOnNext(c1 -> {
                    c1.setCreditType(creditRequest.getCreditType());
                    c1.setStatus(creditRequest.getStatus());
                    c1.setDocumentNumber(creditRequest.getDocumentNumber());
                });
    }

    @Override
    public Mono<PageResponse<CreditRequestResponse>> findCreditRequests(CreditRequestFilter filter) {
        int size = filter.getSize() == 0 ? 10 : filter.getSize();
        int page = filter.getPage() == 0 ? 0 : filter.getPage() - 1;
        Mono<Long> total = repository.countCreditRequests();
        Flux<CreditRequestResponse> results = getResults(size, page);
        return toPageResponse(total, results, page, size);
    }

    private Flux<CreditRequestResponse> getResults(int size, int page) {
        return repository.findCreditRequests(size, page).map(proj -> CreditRequestResponse.builder()
                .id(proj.getId().toString())
                .amount(proj.getAmount())
                .term(proj.getTerm())
                .email(proj.getEmail())
                .status(proj.getStatusName())
                .creditType(proj.getTypeName())
                .build()
        );
    }

    private Mono<PageResponse<CreditRequestResponse>> toPageResponse(Mono<Long> total, Flux<CreditRequestResponse> results, int page, int size) {
        return total.zipWith(results.collectList(), (count, list) -> PageResponse.<CreditRequestResponse>builder()
                .content(list)
                .totalElements(count)
                .page(page == 0 ? 1 : page + 1)
                .size(size)
                .totalPages((int) Math.ceil(count / (double) size))
                .build());
    }
}
