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
                   c.document_number,
                   CONCAT(c.name, ' ', c.last_name) AS full_name,
                   r.email,
                   c.base_salary,
                   s.name AS status_name,
                   t.name AS type_name,
                   t.interest_rate
            FROM requests r
                     INNER JOIN status s ON r.status_id = s.id
                     INNER JOIN types t ON r.type_id = t.id
                     LEFT JOIN clients c ON c.email = r.email
            WHERE (
                (:status IS NOT NULL AND :status <> '' AND s.name = :status)
                OR ((:status IS NULL OR :status = '') AND s.name != 'APPROVED')
            )
            AND (c.document_number ILIKE CONCAT('%', :filter , '%')
                OR c.name ILIKE CONCAT('%', :filter , '%')
                OR c.last_name ILIKE CONCAT('%', :filter , '%')
                OR r.email ILIKE CONCAT('%', :filter , '%')
            )
            ORDER BY r.id
            LIMIT :size OFFSET :offset
            """)
    Flux<CreditRequestResultDTO> findCreditRequests(
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("filter") String filter,
            @Param("status") String status
    );

    @Query("""
            SELECT COUNT(*)
            FROM requests r
            INNER JOIN status s ON r.status_id = s.id
            INNER JOIN types t ON r.type_id = t.id
            LEFT JOIN clients c ON c.email = r.email
            WHERE (
                (:status IS NOT NULL AND :status <> '' AND s.name = :status)
                OR ((:status IS NULL OR :status = '') AND s.name != 'APPROVED')
            )
            AND (c.document_number ILIKE CONCAT('%', :filter , '%')
                OR c.name ILIKE CONCAT('%', :filter , '%')
                OR c.last_name ILIKE CONCAT('%', :filter , '%')
                OR r.email ILIKE CONCAT('%', :filter , '%')
            )
            """)
    Mono<Long> countCreditRequests(@Param("filter") String filter, @Param("status") String status);

    @Query("UPDATE requests SET status_id = :statusId WHERE id = :id RETURNING *")
    Mono<RequestEntity> updateStatusById(@Param("id") UUID id, @Param("statusId") UUID statusId);
}
