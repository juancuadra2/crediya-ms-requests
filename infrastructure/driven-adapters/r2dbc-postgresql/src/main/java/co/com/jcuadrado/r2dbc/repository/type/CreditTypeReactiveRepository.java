package co.com.jcuadrado.r2dbc.repository.type;

import co.com.jcuadrado.r2dbc.entity.CreditTypeEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CreditTypeReactiveRepository extends ReactiveCrudRepository<CreditTypeEntity, UUID>, ReactiveQueryByExampleExecutor<CreditTypeEntity> {
    Mono<CreditTypeEntity> findByName(String name);
}
