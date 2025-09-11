package co.com.jcuadrado.usecase.creditstatus;

import co.com.jcuadrado.model.creditstatus.CreditStatus;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import co.com.jcuadrado.constant.CreditStatusConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CreditStatusUseCaseTest {

    private CreditStatusRepository repository;
    private CreditStatusUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CreditStatusRepository.class);
        useCase = new CreditStatusUseCase(repository);
    }

    @Test
    void testGetByNameSuccess() {
        String name = "Pendiente de revisión";
        CreditStatus creditStatus = CreditStatus.builder()
                .id("ID-123")
                .name("Pendiente de revisión")
                .build();

        when(repository.getCreditStatusByName(name)).thenReturn(Mono.just(creditStatus));

        Mono<CreditStatus> result = useCase.getCreditStatusByName(name);

        StepVerifier.create(result)
                .assertNext(value -> {
                    assertNotNull(value.getId());
                    assertEquals(name, value.getName());
                })
                .verifyComplete();

        Mockito.verify(repository).getCreditStatusByName(name);
    }

     @Test
    void testGetByNameNotFound() {
        String name = "NonExistentType";
        when(repository.getCreditStatusByName(name)).thenReturn(Mono.empty());
        Mono<CreditStatus> result = useCase.getCreditStatusByName(name);
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertEquals(CreditStatusConstants.CREDIT_STATUS_NOT_FOUND, error.getMessage());
                })
                .verify();
        Mockito.verify(repository).getCreditStatusByName(name);
    }
}
