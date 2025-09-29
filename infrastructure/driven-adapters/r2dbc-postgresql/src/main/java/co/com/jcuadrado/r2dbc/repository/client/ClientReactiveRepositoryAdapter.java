package co.com.jcuadrado.r2dbc.repository.client;

import co.com.jcuadrado.model.client.Client;
import co.com.jcuadrado.model.client.gateways.ClientRepository;
import co.com.jcuadrado.r2dbc.entity.ClientEntity;
import co.com.jcuadrado.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
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
        return super.repository.findById(clientEntity.getId())
                .flatMap(existing -> super.repository.update(clientEntity))
                .switchIfEmpty(Mono.defer(() -> super.repository.saveClient(clientEntity)))
                .map(this::toEntity);
    }

    @Override
    public Mono<Void> deleteClient(Client client) {
        UUID id = UUID.fromString(client.getId());
        return super.repository.deleteById(id);
    }
}
