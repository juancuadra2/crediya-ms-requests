package co.com.jcuadrado.r2dbc.repository.request;

import co.com.jcuadrado.r2dbc.entity.RequestEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface RequestReactiveRepository extends ReactiveCrudRepository<RequestEntity, UUID>, ReactiveQueryByExampleExecutor<RequestEntity> {
}
