package co.com.jcuadrado.usecase.creditrequest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import co.com.jcuadrado.model.auth.AuthInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import co.com.jcuadrado.model.creditstatus.CreditStatus;
import co.com.jcuadrado.model.credittype.CreditType;
import co.com.jcuadrado.model.user.User;
import co.com.jcuadrado.usecase.creditstatus.CreditStatusUseCase;
import co.com.jcuadrado.usecase.credittype.CreditTypeUseCase;
import co.com.jcuadrado.usecase.user.UserUseCase;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CreditRequestUseCaseTest {

    @Mock
    private CreditRequestRepository creditRequestRepository;

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private CreditStatusUseCase creditStatusUseCase;

    @Mock
    private CreditTypeUseCase creditTypeUseCase;

    @InjectMocks
    private CreditRequestUseCase creditRequestUseCase;

    @Test
    void testSaveCreditRequest() {
        AuthInfo authInfo = AuthInfo.builder()
                .token("Token")
                .email("admin@mail.com")
                .role("ADMIN")
                .build();

        CreditRequest creditRequest = CreditRequest.builder()
                .amount(new BigDecimal("1000"))
                .term(12)
                .documentNumber("123456789")
                .email("test@example.com")
                .creditType("PERSONAL")
                .status(CreditStatusEnum.PENDING.name())
                .build();

        when(userUseCase.getUserByDocumentNumber(creditRequest.getDocumentNumber(), authInfo.getToken()))
                .thenReturn(Mono.just(User.builder()
                        .documentNumber(creditRequest.getDocumentNumber())
                        .email(creditRequest.getEmail())
                        .build()));

        when(creditStatusUseCase.getCreditStatusByName(creditRequest.getStatus()))
                .thenReturn(Mono.just(CreditStatus.builder()
                        .id("status-id-123")
                        .name(CreditStatusEnum.PENDING.name())
                        .build()));

        when(creditTypeUseCase.getCreditTypeByName(creditRequest.getCreditType()))
                .thenReturn(Mono.just(CreditType.builder()
                        .id("type-id-123")
                        .name(creditRequest.getCreditType())
                        .build()));

        when(creditRequestRepository.saveCreditRequest(creditRequest)).thenReturn(Mono.just(creditRequest));

        Mono<CreditRequest> result = creditRequestUseCase.saveCreditRequest(creditRequest, authInfo);

        StepVerifier.create(result)
                .expectNext(creditRequest)
                .verifyComplete();

        verify(creditRequestRepository).saveCreditRequest(creditRequest);
        verify(userUseCase).getUserByDocumentNumber(creditRequest.getDocumentNumber(), authInfo.getToken());
    }
}
