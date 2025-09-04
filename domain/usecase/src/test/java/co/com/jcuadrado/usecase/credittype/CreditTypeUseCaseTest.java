package co.com.jcuadrado.usecase.credittype;

import co.com.jcuadrado.constant.CreditTypeConstants;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.credittype.CreditType;
import co.com.jcuadrado.model.credittype.gateways.CreditTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class CreditTypeUseCaseTest {

    private CreditTypeRepository repository;
    private CreditTypeUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CreditTypeRepository.class);
        useCase = new CreditTypeUseCase(repository);
    }

    @Test
    void testGetByNameSuccess() {
        String name = "Personal";
        CreditType creditType = CreditType.builder()
                .id("")
                .name("Personal")
                .maxAmount(BigDecimal.valueOf(200000.00))
                .minAmount(BigDecimal.valueOf(0.00))
                .build();

        when(repository.getCreditTypeByName(name)).thenReturn(Mono.just(creditType));

        Mono<CreditType> result = useCase.getCreditTypeByName(name);

        result.subscribe(value -> {
            assert value.getName().equals(name);
        });

        Mockito.verify(repository).getCreditTypeByName(name);
    }

    @Test
    void testGetByNameNotFound() {
        String name = "NonExistentType";

        when(repository.getCreditTypeByName(name)).thenReturn(Mono.empty());

        Mono<CreditType> result = useCase.getCreditTypeByName(name);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    BusinessException businessException = (BusinessException) error;
                    assertEquals(CreditTypeConstants.CREDIT_TYPE_NOT_FOUND, businessException.getMessage());
                })
                .verify();

        Mockito.verify(repository).getCreditTypeByName(name);
    }

}
