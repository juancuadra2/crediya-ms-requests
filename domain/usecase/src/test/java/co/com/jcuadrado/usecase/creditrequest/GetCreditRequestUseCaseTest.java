package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.constant.AuthConstant;
import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.constant.RoleEnum;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.common.PageResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequestFilter;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCreditRequestUseCaseTest {

    @Mock
    private CreditRequestRepository creditRequestRepository;

    @InjectMocks
    private GetCreditRequestUseCase getCreditRequestUseCase;

    @Test
    void getCreditRequests_shouldReturnPageResponse() {
        CreditRequestFilter filter = CreditRequestFilter.builder()
                .filter("")
                .page(1)
                .size(10)
                .build();

        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();

        CreditRequestResponse creditRequestResponse = CreditRequestResponse.builder()
                .id("1")
                .documentNumber("123456789")
                .email("johndoe@mail.com")
                .fullName("John Doe")
                .baseSalary(BigDecimal.valueOf(120000))
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .interestRate(BigDecimal.valueOf(1.5))
                .monthlyPayment(BigDecimal.valueOf(8560.75))
                .status(CreditStatusEnum.PENDING.name())
                .creditType("PERSONAL")
                .build();

        PageResponse<CreditRequestResponse> pageResponse = PageResponse.<CreditRequestResponse>builder()
                .content(List.of(creditRequestResponse))
                .page(1)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(creditRequestRepository.findCreditRequests(filter)).thenReturn(Mono.just(pageResponse));

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .expectNext(pageResponse)
                .verifyComplete();

        verify(creditRequestRepository).findCreditRequests(filter);
    }

    @Test
    void getCreditRequests_shouldThrowException_forbidden() {
        CreditRequestFilter filter = CreditRequestFilter.builder().build();
        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.CLIENT.name())
                .build();

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> {
                    BusinessException exception = (BusinessException) throwable;
                    assertNotNull(exception);
                    assertEquals(AuthConstant.ACCESS_DENIED, exception.getMessage());
                })
                .verify();
    }

    @Test
    void  getCreditRequests_whenContentIsNull_shouldReturnPageResponseWithNullContent() {
        CreditRequestFilter filter = CreditRequestFilter.builder()
                .filter("")
                .page(1)
                .size(10)
                .build();

        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();

        PageResponse<CreditRequestResponse> pageResponse = PageResponse.<CreditRequestResponse>builder()
                .content(null)
                .page(1)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(creditRequestRepository.findCreditRequests(filter)).thenReturn(Mono.just(pageResponse));

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .expectNext(pageResponse)
                .verifyComplete();

        verify(creditRequestRepository).findCreditRequests(filter);
    }

    @Test
    void getCreditRequests_whenAmountIsNull_shouldCalculateMonthlyPaymentAsZero() {
        CreditRequestFilter filter = CreditRequestFilter.builder()
                .filter("")
                .page(1)
                .size(10)
                .build();

        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();

        CreditRequestResponse creditRequestResponse = CreditRequestResponse.builder()
                .id("1")
                .documentNumber("123456789")
                .email("johndoe@mail.com")
                .fullName("John Doe")
                .baseSalary(BigDecimal.valueOf(120000))
                .amount(null)
                .term(12)
                .interestRate(BigDecimal.valueOf(1.5))
                .monthlyPayment(BigDecimal.valueOf(8560.75))
                .status(CreditStatusEnum.PENDING.name())
                .creditType("PERSONAL")
                .build();

        PageResponse<CreditRequestResponse> pageResponse = PageResponse.<CreditRequestResponse>builder()
                .content(List.of(creditRequestResponse))
                .page(1)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(creditRequestRepository.findCreditRequests(filter)).thenReturn(Mono.just(pageResponse));

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .assertNext(page -> {
                    assertNotNull(page);
                    assertNotNull(page.getContent());
                    assertEquals(1, page.getContent().size());
                    CreditRequestResponse cr = page.getContent().getFirst();
                    assertNotNull(cr);
                 })
                .verifyComplete();

        verify(creditRequestRepository).findCreditRequests(filter);
    }

    @Test
    void getCreditRequests_whenMonthlyInterestRatePercentIsNull_shouldCalculateMonthlyPaymentAsZero(){
        CreditRequestFilter filter = CreditRequestFilter.builder()
                .filter("")
                .page(1)
                .size(10)
                .build();

        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();

        CreditRequestResponse creditRequestResponse = CreditRequestResponse.builder()
                .id("1")
                .documentNumber("123456789")
                .email("johndoe@mail.com")
                .fullName("John Doe")
                .baseSalary(BigDecimal.valueOf(120000))
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .interestRate(BigDecimal.valueOf(1.5))
                .monthlyPayment(null)
                .status(CreditStatusEnum.PENDING.name())
                .creditType("PERSONAL")
                .build();

        PageResponse<CreditRequestResponse> pageResponse = PageResponse.<CreditRequestResponse>builder()
                .content(List.of(creditRequestResponse))
                .page(1)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(creditRequestRepository.findCreditRequests(filter)).thenReturn(Mono.just(pageResponse));

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .assertNext(page -> {
                    assertNotNull(page);
                    assertNotNull(page.getContent());
                    assertEquals(1, page.getContent().size());
                    CreditRequestResponse cr = page.getContent().getFirst();
                    assertNotNull(cr);
                })
                .verifyComplete();

        verify(creditRequestRepository).findCreditRequests(filter);
    }

    @Test
    void getCreditRequests_whenTermIsNull_shouldCalculateMonthlyPaymentAsZero() {
        CreditRequestFilter filter = CreditRequestFilter.builder()
                .filter("")
                .page(1)
                .size(10)
                .build();

        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();

        CreditRequestResponse creditRequestResponse = CreditRequestResponse.builder()
                .id("1")
                .documentNumber("123456789")
                .email("johndoe@mail.com")
                .fullName("John Doe")
                .baseSalary(BigDecimal.valueOf(120000))
                .amount(BigDecimal.valueOf(100000))
                .term(null)
                .interestRate(BigDecimal.valueOf(1.5))
                .monthlyPayment(BigDecimal.valueOf(8560.75))
                .status(CreditStatusEnum.PENDING.name())
                .creditType("PERSONAL")
                .build();

        PageResponse<CreditRequestResponse> pageResponse = PageResponse.<CreditRequestResponse>builder()
                .content(List.of(creditRequestResponse))
                .page(1)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(creditRequestRepository.findCreditRequests(filter)).thenReturn(Mono.just(pageResponse));

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .assertNext(page -> {
                    assertNotNull(page);
                    assertNotNull(page.getContent());
                    assertEquals(1, page.getContent().size());
                    CreditRequestResponse cr = page.getContent().getFirst();
                    assertNotNull(cr);
                })
                .verifyComplete();

        verify(creditRequestRepository).findCreditRequests(filter);

    }

    @Test
    void getCreditRequests_whenTermIsMinorToZero_shouldCalculateMonthlyPaymentAsZero() {
        CreditRequestFilter filter = CreditRequestFilter.builder()
                .filter("")
                .page(1)
                .size(10)
                .build();

        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();

        CreditRequestResponse creditRequestResponse = CreditRequestResponse.builder()
                .id("1")
                .documentNumber("123456789")
                .email("johndoe@mail.com")
                .fullName("John Doe")
                .baseSalary(BigDecimal.valueOf(120000))
                .amount(BigDecimal.valueOf(100000))
                .term(-1)
                .interestRate(BigDecimal.valueOf(1.5))
                .monthlyPayment(BigDecimal.valueOf(8560.75))
                .status(CreditStatusEnum.PENDING.name())
                .creditType("PERSONAL")
                .build();

        PageResponse<CreditRequestResponse> pageResponse = PageResponse.<CreditRequestResponse>builder()
                .content(List.of(creditRequestResponse))
                .page(1)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(creditRequestRepository.findCreditRequests(filter)).thenReturn(Mono.just(pageResponse));

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .assertNext(page -> {
                    assertNotNull(page);
                    assertNotNull(page.getContent());
                    assertEquals(1, page.getContent().size());
                    CreditRequestResponse cr = page.getContent().getFirst();
                    assertNotNull(cr);
                })
                .verifyComplete();

        verify(creditRequestRepository).findCreditRequests(filter);
    }

    @Test
    void getCreditRequests_whenMonthlyInterestRateIsNull_shouldCalculateMonthlyPaymentAsAmountDividedByTerm() {
        CreditRequestFilter filter = CreditRequestFilter.builder()
                .filter("")
                .page(1)
                .size(10)
                .build();

        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();

        CreditRequestResponse creditRequestResponse = CreditRequestResponse.builder()
                .id("1")
                .documentNumber("123456789")
                .email("johndoe@mail.com")
                .fullName("John Doe")
                .baseSalary(BigDecimal.valueOf(120000))
                .amount(BigDecimal.valueOf(100000))
                .term(-1)
                .interestRate(null)
                .monthlyPayment(BigDecimal.valueOf(8560.75))
                .status(CreditStatusEnum.PENDING.name())
                .creditType("PERSONAL")
                .build();

        PageResponse<CreditRequestResponse> pageResponse = PageResponse.<CreditRequestResponse>builder()
                .content(List.of(creditRequestResponse))
                .page(1)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(creditRequestRepository.findCreditRequests(filter)).thenReturn(Mono.just(pageResponse));

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .assertNext(page -> {
                    assertNotNull(page);
                    assertNotNull(page.getContent());
                    assertEquals(1, page.getContent().size());
                    CreditRequestResponse cr = page.getContent().getFirst();
                    assertNotNull(cr);
                })
                .verifyComplete();

        verify(creditRequestRepository).findCreditRequests(filter);
    }

    @Test
    void getCreditRequests_whenMonthlyInterestRateIsZero_shouldCalculateMonthlyPaymentAsAmountDividedByTerm() {
        CreditRequestFilter filter = CreditRequestFilter.builder()
                .filter("")
                .page(1)
                .size(10)
                .build();

        AuthInfo authInfo = AuthInfo.builder()
                .role(RoleEnum.ADVISER.name())
                .build();

        CreditRequestResponse creditRequestResponse = CreditRequestResponse.builder()
                .id("1")
                .documentNumber("123456789")
                .email("johndoe@mail.com")
                .fullName("John Doe")
                .baseSalary(BigDecimal.valueOf(120000))
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .interestRate(BigDecimal.ZERO)
                .monthlyPayment(BigDecimal.valueOf(8560.75))
                .status(CreditStatusEnum.PENDING.name())
                .creditType("PERSONAL")
                .build();

        PageResponse<CreditRequestResponse> pageResponse = PageResponse.<CreditRequestResponse>builder()
                .content(List.of(creditRequestResponse))
                .page(1)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(creditRequestRepository.findCreditRequests(filter)).thenReturn(Mono.just(pageResponse));

        Mono<PageResponse<CreditRequestResponse>> result = getCreditRequestUseCase.getCreditRequests(filter, authInfo);

        StepVerifier.create(result)
                .assertNext(page -> {
                    assertNotNull(page);
                    assertNotNull(page.getContent());
                    assertEquals(1, page.getContent().size());
                    CreditRequestResponse cr = page.getContent().getFirst();
                    assertNotNull(cr);
                })
                .verifyComplete();

        verify(creditRequestRepository).findCreditRequests(filter);
    }
}
