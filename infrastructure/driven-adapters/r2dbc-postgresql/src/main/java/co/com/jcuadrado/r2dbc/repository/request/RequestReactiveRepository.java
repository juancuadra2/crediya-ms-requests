package co.com.jcuadrado.r2dbc.repository.request;

import co.com.jcuadrado.r2dbc.dto.CreditRequestResultDTO;
import co.com.jcuadrado.r2dbc.entity.RequestEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RequestReactiveRepository extends ReactiveCrudRepository<RequestEntity, UUID>, ReactiveQueryByExampleExecutor<RequestEntity> {

    @Query("""
            SELECT r.id,
                   r.amount,
                   r.term,
                   r.email,
                   s.name AS status_name,
                   t.name AS type_name,
                   t.interest_rate
            FROM requests r
            INNER JOIN status s ON r.status_id = s.id
            INNER JOIN types t ON r.type_id = t.id
            WHERE s.name != 'APPROVED'
            ORDER BY r.id
            LIMIT :size OFFSET :offset
            """)
    Flux<CreditRequestResultDTO> findCreditRequests(@Param("size") int size, @Param("offset") int offset);

    @Query("""
            SELECT COUNT(*)
            FROM requests r
            INNER JOIN status s ON r.status_id = s.id
            INNER JOIN types t ON r.type_id = t.id
            WHERE s.name != 'APPROVED'
            """)
    Mono<Long> countCreditRequests();
}
