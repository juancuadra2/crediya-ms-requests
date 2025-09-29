package co.com.jcuadrado.r2dbc.repository.status;

import co.com.jcuadrado.r2dbc.entity.CreditStatusEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CreditStatusReactiveRepository extends ReactiveCrudRepository<CreditStatusEntity, UUID>, ReactiveQueryByExampleExecutor<CreditStatusEntity> {
    Mono<CreditStatusEntity> findByName(String name);
}
