package co.com.jcuadrado.r2dbc.repository.status;

import co.com.jcuadrado.model.creditstatus.CreditStatus;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import co.com.jcuadrado.r2dbc.entity.CreditStatusEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class CreditStatusRepositoryAdapter extends ReactiveAdapterOperations<
        CreditStatus,
        CreditStatusEntity,
        UUID,
        CreditStatusReactiveRepository
        > implements CreditStatusRepository {

    protected CreditStatusRepositoryAdapter(CreditStatusReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, CreditStatus.class));
    }

    @Override
    public Mono<CreditStatus> getCreditStatusByName(String name) {
        return super.repository.findByName(name).map(this::toEntity);
    }
}
