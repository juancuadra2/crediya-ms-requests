package co.com.jcuadrado.r2dbc.repository.status;

import co.com.jcuadrado.model.creditstatus.CreditStatus;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import co.com.jcuadrado.r2dbc.constant.CreditStatusRepositoryConstants;
import co.com.jcuadrado.r2dbc.entity.CreditStatusEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Log4j2
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
        log.info(CreditStatusRepositoryConstants.GET_CREDIT_STATUS_ENTRY, name);
        return super.repository.findByName(name)
                .map(this::toEntity)
                .doOnNext(cs -> log.info(CreditStatusRepositoryConstants.GET_CREDIT_STATUS_SUCCESS, cs.getName()))
                .doOnError(e -> log.error(CreditStatusRepositoryConstants.GET_CREDIT_STATUS_ERROR, e));
    }
}
