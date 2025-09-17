package co.com.jcuadrado.service;

import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditstatus.CreditStatus;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditStatusServiceTest {

    @Mock
    private CreditStatusRepository creditStatusRepository;

    @Test
    void validateSuccess() {
        CreditStatus creditStatus = CreditStatus.builder()
                .id("ID-123")
                .name(CreditStatusEnum.PENDING.name())
                .build();

        CreditRequest creditRequest = CreditRequest.builder()
                .status(CreditStatusEnum.PENDING.name())
                .build();

        when(creditStatusRepository.getCreditStatusByName(CreditStatusEnum.PENDING.name())).thenReturn(Mono.just(creditStatus));


        Mono<CreditRequest> creditRequestMono = CreditStatusService.validateAndSetId(creditRequest, creditStatusRepository);

        StepVerifier.create(creditRequestMono)
                .assertNext(updatedCreditRequest -> {
                    assertNotNull(updatedCreditRequest.getStatus());
                    assertEquals("ID-123", updatedCreditRequest.getStatus());
                })
                .verifyComplete();

        verify(creditStatusRepository).getCreditStatusByName(CreditStatusEnum.PENDING.name());
    }


}
