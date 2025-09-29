package co.com.jcuadrado.r2dbc.repository.client;

import co.com.jcuadrado.r2dbc.entity.ClientEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ClientReactiveRepository extends ReactiveCrudRepository<ClientEntity, UUID>, ReactiveQueryByExampleExecutor<ClientEntity> {

    @Query("""
                UPDATE clients
                SET document_number = :#{#c.documentNumber},
                    name = :#{#c.name},
                    last_name = :#{#c.lastName},
                    email = :#{#c.email},
                    base_salary = :#{#c.baseSalary}
                WHERE id = :#{#c.id}
                RETURNING *
            """)
    Mono<ClientEntity> update(@Param("c") ClientEntity client);

    @Query("""
                INSERT INTO clients (id, document_number, name, last_name, email, base_salary)
                VALUES (:#{#c.id}, :#{#c.documentNumber}, :#{#c.name}, :#{#c.lastName}, :#{#c.email}, :#{#c.baseSalary})
                RETURNING *
            """)
    Mono<ClientEntity> saveClient(@Param("c") ClientEntity client);

}
