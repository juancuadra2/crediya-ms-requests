package co.com.jcuadrado.r2dbc.repository.client;

import co.com.jcuadrado.model.client.Client;
import co.com.jcuadrado.model.client.gateways.ClientRepository;
import co.com.jcuadrado.r2dbc.constant.ClientRepositoryConstants;
import co.com.jcuadrado.r2dbc.entity.ClientEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Log4j2
public class ClientReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Client,
        ClientEntity,
        UUID,
        ClientReactiveRepository
        > implements ClientRepository {

    protected ClientReactiveRepositoryAdapter(ClientReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Client.class));
    }

    @Override
    public Mono<Client> saveClient(Client client) {
        ClientEntity clientEntity = toData(client);
        clientEntity.setId(UUID.fromString(client.getId()));
        log.info(ClientRepositoryConstants.SAVE_CLIENT_ENTRY, client.getId());
        return super.repository.findById(clientEntity.getId())
                .flatMap(existing -> super.repository.update(clientEntity)
                        .doOnNext(updated -> log.info(ClientRepositoryConstants.SAVE_CLIENT_UPDATED, updated.getId()))
                )
                .switchIfEmpty(Mono.defer(() -> super.repository.saveClient(clientEntity)
                        .doOnNext(created -> log.info(ClientRepositoryConstants.SAVE_CLIENT_CREATED, created.getId()))
                ))
                .map(this::toEntity)
                .doOnError(e -> log.error(ClientRepositoryConstants.SAVE_CLIENT_ERROR, e));
    }

    @Override
    public Mono<Void> deleteClient(Client client) {
        UUID id = UUID.fromString(client.getId());
        log.info(ClientRepositoryConstants.DELETE_CLIENT_ENTRY, client.getId());
        return super.repository.deleteById(id)
                .doOnSuccess(unused -> log.info(ClientRepositoryConstants.DELETE_CLIENT_SUCCESS, client.getId()))
                .doOnError(e -> log.error(ClientRepositoryConstants.DELETE_CLIENT_ERROR, e));
    }
}
