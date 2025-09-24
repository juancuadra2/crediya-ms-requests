package co.com.jcuadrado.usecase.client;

import co.com.jcuadrado.model.client.Client;
import co.com.jcuadrado.model.client.gateways.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientUseCase clientUseCase;

    @Test
    void save_shouldSaveClient() {
        Client client = Client.builder()
                .id("1")
                .documentNumber("123456789")
                .name("John")
                .lastName("Doe")
                .email("johndoe@mail.com")
                .baseSalary(BigDecimal.valueOf(200000))
                .build();

        when(clientRepository.saveClient(client)).thenReturn(Mono.just(client));

        Mono<Client> result = clientUseCase.save(client);

        StepVerifier.create(result)
                .expectNext(client)
                .verifyComplete();

        verify(clientRepository).saveClient(client);
    }

    @Test
    void delete_shouldDeleteClient() {
        Client client = Client.builder()
                .id("1")
                .documentNumber("123456789")
                .name("John")
                .lastName("Doe")
                .email("johndoe@mail.com")
                .baseSalary(BigDecimal.valueOf(200000))
                .build();

        when(clientRepository.deleteClient(client)).thenReturn(Mono.empty());

        Mono<Void> result = clientUseCase.delete(client);

        StepVerifier.create(result)
                .verifyComplete();

        verify(clientRepository).deleteClient(client);
    }
}
