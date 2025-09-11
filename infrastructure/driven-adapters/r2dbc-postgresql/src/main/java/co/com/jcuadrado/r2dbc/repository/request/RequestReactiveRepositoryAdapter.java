package co.com.jcuadrado.r2dbc.repository.request;

import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.r2dbc.entity.RequestEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
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
}
