package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.constant.AuthConstant;
import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.constant.RoleEnum;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.model.creditrequest.gateways.NotifyCreditRequest;
import co.com.jcuadrado.model.creditstatus.CreditStatus;
import co.com.jcuadrado.model.creditstatus.gateways.CreditStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCreditRequestUseCaseTest {

    @Mock
    private CreditRequestRepository creditRequestRepository;

    @Mock
    private CreditStatusRepository creditStatusRepository;

    @Mock
    private NotifyCreditRequest notifyCreditRequest;

    @InjectMocks
    private UpdateCreditRequestUseCase updateCreditRequestUseCase;

    @Test
    void changeStatus_shouldUpdateStatusAndNotify() {
        String id = "123";
        String status = "REJECTED";
        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();
        CreditStatus creditStatus = CreditStatus.builder()
                .id("1")
                .name(CreditStatusEnum.REJECTED.name())
                .build();

        CreditRequest creditRequest = CreditRequest.builder()
                .id(id)
                .status(creditStatus.getId())
                .build();

        CreditRequestResponse creditRequestResponse = CreditRequestResponse.builder()
                .id(id)
                .status(status)
                .build();

        when(creditStatusRepository.getCreditStatusByName(status)).thenReturn(Mono.just(creditStatus));
        when(creditRequestRepository.updateCreditRequestStatus(creditRequest)).thenReturn(Mono.just(creditRequestResponse));
        when(notifyCreditRequest.notify(creditRequestResponse)).thenReturn(Mono.empty());

        Mono<CreditRequestResponse> result = updateCreditRequestUseCase.changeStatus(id, status, authInfo);

        StepVerifier.create(result)
                .expectNext(creditRequestResponse)
                .verifyComplete();

        verify(creditStatusRepository).getCreditStatusByName(status);
        verify(creditRequestRepository).updateCreditRequestStatus(creditRequest);
        verify(notifyCreditRequest).notify(creditRequestResponse);
    }

    @Test
    void changeStatus_shouldThrowException_forbiddenRole() {
        String id = "123";
        String status = "REJECTED";
        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.CLIENT.name())
                .build();

        Mono<CreditRequestResponse> result = updateCreditRequestUseCase.changeStatus(id, status, authInfo);

        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> {
                    BusinessException exception = (BusinessException) throwable;
                    assertNotNull(exception);
                    assertEquals(AuthConstant.ACCESS_DENIED, exception.getMessage());
                })
                .verify();
    }
}
