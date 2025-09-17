package co.com.jcuadrado.r2dbc.repository.type;

import co.com.jcuadrado.model.credittype.CreditType;
import co.com.jcuadrado.model.credittype.gateways.CreditTypeRepository;
import co.com.jcuadrado.r2dbc.constant.CreditTypeRepositoryConstants;
import co.com.jcuadrado.r2dbc.entity.CreditTypeEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Log4j2
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
        log.info(CreditTypeRepositoryConstants.GET_CREDIT_TYPE_ENTRY, name);
        return super.repository.findByName(name).map(this::toEntity)
                .doOnNext(ct -> log.info(CreditTypeRepositoryConstants.GET_CREDIT_TYPE_SUCCESS, ct.getName()))
                .doOnError(e -> log.error(CreditTypeRepositoryConstants.GET_CREDIT_TYPE_ERROR, e));
    }
}
