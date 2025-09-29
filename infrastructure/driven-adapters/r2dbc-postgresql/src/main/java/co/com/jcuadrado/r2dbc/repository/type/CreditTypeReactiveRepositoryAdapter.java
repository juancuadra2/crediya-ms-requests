package co.com.jcuadrado.r2dbc.repository.type;

import co.com.jcuadrado.model.credittype.CreditType;
import co.com.jcuadrado.model.credittype.gateways.CreditTypeRepository;
import co.com.jcuadrado.r2dbc.entity.CreditTypeEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class CreditTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        CreditType,
        CreditTypeEntity,
        UUID,
        CreditTypeReactiveRepository
        > implements CreditTypeRepository {

    protected CreditTypeReactiveRepositoryAdapter(CreditTypeReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, CreditType.class));
    }

    @Override
    public Mono<CreditType> getCreditTypeByName(String name) {
        return super.repository.findByName(name).map(this::toEntity);
    }
}
