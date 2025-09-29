package co.com.jcuadrado.usecase.client;

import co.com.jcuadrado.model.client.Client;
import co.com.jcuadrado.model.client.gateways.ClientRepository;
import reactor.core.publisher.Mono;

public record ClientUseCase(ClientRepository clientRepository) {

    public Mono<Client> save(Client client){
        return clientRepository.saveClient(client);
    }

    public Mono<Void> delete(Client client){
        return clientRepository.deleteClient(client);
    }

}
