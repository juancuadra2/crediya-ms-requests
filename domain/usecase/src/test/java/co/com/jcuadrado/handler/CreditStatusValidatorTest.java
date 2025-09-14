package co.com.jcuadrado.handler;

import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditstatus.CreditStatus;
import co.com.jcuadrado.usecase.creditstatus.CreditStatusUseCase;
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
class CreditStatusValidatorTest {

    @Mock
    private CreditStatusUseCase creditStatusUseCase;

    @Test
    void validateSuccess() {
        CreditStatus creditStatus = CreditStatus.builder()
                .id("ID-123")
                .name(CreditStatusEnum.PENDING.name())
                .build();

        CreditRequest creditRequest = CreditRequest.builder()
                .status(CreditStatusEnum.PENDING.name())
                .build();

        when(creditStatusUseCase.getCreditStatusByName(CreditStatusEnum.PENDING.name())).thenReturn(Mono.just(creditStatus));


        Mono<CreditRequest> creditRequestMono = CreditStatusValidator.validateAndSetId(creditRequest, creditStatusUseCase);

        StepVerifier.create(creditRequestMono)
                .assertNext(updatedCreditRequest -> {
                    assertNotNull(updatedCreditRequest.getStatus());
                    assertEquals("ID-123", updatedCreditRequest.getStatus());
                })
                .verifyComplete();

        verify(creditStatusUseCase).getCreditStatusByName(CreditStatusEnum.PENDING.name());
    }


}
