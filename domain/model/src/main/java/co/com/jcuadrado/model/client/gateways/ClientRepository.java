package co.com.jcuadrado.model.client.gateways;

import co.com.jcuadrado.model.client.Client;
import reactor.core.publisher.Mono;

public interface ClientRepository {
    Mono<Client> saveClient(Client client);
    Mono<Void> deleteClient(Client client);
}
