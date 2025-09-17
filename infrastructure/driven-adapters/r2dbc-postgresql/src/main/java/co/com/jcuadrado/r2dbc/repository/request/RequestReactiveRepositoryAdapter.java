package co.com.jcuadrado.r2dbc.repository.request;

import co.com.jcuadrado.model.common.PageResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.CreditRequestFilter;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.r2dbc.constant.RequestRepositoryConstants;
import co.com.jcuadrado.r2dbc.constant.RequestRepositoryNumbers;
import co.com.jcuadrado.r2dbc.entity.RequestEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.util.Objects;
import java.util.UUID;

@Repository
@Log4j2
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
        log.info(RequestRepositoryConstants.SAVE_CREDIT_REQUEST_ENTRY, creditRequest.getDocumentNumber());
        RequestEntity requestEntity = toData(creditRequest);
        requestEntity.setStatusId(UUID.fromString(creditRequest.getStatus()));
        requestEntity.setTypeId(UUID.fromString(creditRequest.getCreditType()));
        return repository.save(requestEntity)
                .map(this::toEntity)
                .doOnNext(c1 -> {
                    c1.setCreditType(creditRequest.getCreditType());
                    c1.setStatus(creditRequest.getStatus());
                    c1.setDocumentNumber(creditRequest.getDocumentNumber());
                    log.info(RequestRepositoryConstants.SAVE_CREDIT_REQUEST_SAVED, c1.getId());
                })
                .doOnError(e -> log.error(RequestRepositoryConstants.SAVE_CREDIT_REQUEST_ERROR, e.getMessage()));
    }

    @Override
    public Mono<PageResponse<CreditRequestResponse>> findCreditRequests(CreditRequestFilter creditRequestFilter) {
        String filter = Objects.toString(creditRequestFilter.getFilter(), "");
        int size = creditRequestFilter.getSize() == RequestRepositoryNumbers.DEFAULT_PAGE ? RequestRepositoryNumbers.DEFAULT_PAGE_SIZE : creditRequestFilter.getSize();
        int page = creditRequestFilter.getPage() == RequestRepositoryNumbers.DEFAULT_PAGE ? RequestRepositoryNumbers.DEFAULT_PAGE : creditRequestFilter.getPage() - 1;
        log.info(RequestRepositoryConstants.FIND_CREDIT_REQUESTS_ENTRY, filter, creditRequestFilter.getStatus(), page, size);
        Mono<Long> total = repository.countCreditRequests(filter, creditRequestFilter.getStatus())
                .doOnError(e -> log.error(RequestRepositoryConstants.COUNT_CREDIT_REQUESTS_ERROR, e.getMessage()));
        Flux<CreditRequestResponse> results = getResults(size, page, filter, creditRequestFilter.getStatus())
                .doOnError(e -> log.error(RequestRepositoryConstants.FIND_CREDIT_REQUESTS_ERROR, e.getMessage()));
        return toPageResponse(total, results, page, size);
    }

    @Override
    public Mono<CreditRequest> updateCreditRequestStatus(CreditRequest creditRequest) {
        log.info(RequestRepositoryConstants.UPDATE_STATUS_ENTRY, creditRequest.getId());
        UUID creditRequestId = UUID.fromString(creditRequest.getId());
        UUID statusId = UUID.fromString(creditRequest.getStatus());
        return super.repository.updateStatusById(creditRequestId, statusId)
                .flatMap(c1 -> super.repository.findById(c1.getId())
                        .map(this::toEntity)
                        .map(result -> {
                            result.setStatus(String.valueOf(c1.getStatusId()));
                            result.setCreditType(String.valueOf(c1.getTypeId()));
                            return result;
                        })
                )
                .doOnNext(c1 -> log.info(RequestRepositoryConstants.UPDATE_STATUS_SUCCESS, c1.getId()))
                .doOnError(e -> log.error(RequestRepositoryConstants.UPDATE_STATUS_ERROR, e.getMessage()));
    }

    private Flux<CreditRequestResponse> getResults(int size, int page, @NonNull String filter, String status) {
        return repository.findCreditRequests(size, page, filter, status).map(proj -> CreditRequestResponse.builder()
                        .id(proj.getId().toString())
                        .amount(proj.getAmount())
                        .term(proj.getTerm())
                        .documentNumber(proj.getDocumentNumber())
                        .fullName(proj.getFullName())
                        .baseSalary(proj.getBaseSalary())
                        .email(proj.getEmail())
                        .status(proj.getStatusName())
                        .creditType(proj.getTypeName())
                        .interestRate(proj.getInterestRate())
                        .build()
                );
    }

    private Mono<PageResponse<CreditRequestResponse>> toPageResponse(Mono<Long> total, Flux<CreditRequestResponse> results, int page, int size) {
        return total.zipWith(results.collectList(), (count, list) -> PageResponse.<CreditRequestResponse>builder()
                .content(list)
                .totalElements(count)
                .page(page == RequestRepositoryNumbers.DEFAULT_PAGE ? RequestRepositoryNumbers.FIRST_PAGE : page + 1)
                .size(size)
                .totalPages((int) Math.ceil(count / (double) size))
                .build());
    }
}
