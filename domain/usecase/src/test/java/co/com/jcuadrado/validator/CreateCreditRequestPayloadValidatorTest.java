package co.com.jcuadrado.validator;

import co.com.jcuadrado.constant.CreditRequestConstants;
import co.com.jcuadrado.constant.CreditStatusEnum;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

class CreateCreditRequestPayloadValidatorTest {

    @Test
    void testSuccess() {
        CreditRequest creditRequest = CreditRequest.builder()
                .amount(BigDecimal.TEN)
                .term(12)
                .documentNumber("123456789")
                .status(CreditStatusEnum.PENDING.name())
                .creditType("PERSONAL")
                .build();

        Mono<CreditRequest> result = CreateCreditRequestPayloadValidator.validate(creditRequest);

        StepVerifier.create(result)
                .expectNext(creditRequest)
                .verifyComplete();
    }

    @Test
    void testFailCreditRequestNull() {
        Mono<CreditRequest> result = CreateCreditRequestPayloadValidator.validate(null);

        StepVerifier.create(result)
                .expectErrorMatches(error -> {
                    BusinessException businessException = (BusinessException) error;
                    return businessException.getMessage().equals(CreditRequestConstants.CREDIT_REQUEST_NOT_NULL);
                })
                .verify();
    }

    @Test
    void testFailAmountNull() {
        CreditRequest creditRequest = CreditRequest.builder().build();

        Mono<CreditRequest> result = CreateCreditRequestPayloadValidator.validate(creditRequest);

        StepVerifier.create(result)
                .expectErrorMatches(error -> {
                    BusinessException businessException = (BusinessException) error;
                    return businessException.getMessage().equals(CreditRequestConstants.AMOUNT_REQUIRED);
                })
                .verify();
    }

    @Test
    void testFailLimitDateNull() {
        CreditRequest creditRequest = CreditRequest.builder()
                .amount(BigDecimal.TEN)
                .build();

        Mono<CreditRequest> result = CreateCreditRequestPayloadValidator.validate(creditRequest);

        StepVerifier.create(result)
                .expectErrorMatches(error -> {
                    BusinessException businessException = (BusinessException) error;
                    return businessException.getMessage().equals(CreditRequestConstants.TERM_REQUIRED);
                })
                .verify();
    }

    @Test
    void testFailDocumentNumberNull() {
        CreditRequest creditRequest = CreditRequest.builder()
                .amount(BigDecimal.TEN)
                .term(12)
                .build();

        Mono<CreditRequest> result = CreateCreditRequestPayloadValidator.validate(creditRequest);

        StepVerifier.create(result)
                .expectErrorMatches(error -> {
                    BusinessException businessException = (BusinessException) error;
                    return businessException.getMessage().equals(CreditRequestConstants.DOCUMENT_NUMBER_REQUIRED);
                })
                .verify();
    }

    @Test
    void testFailStatusNull() {
        CreditRequest creditRequest = CreditRequest.builder()
                .amount(BigDecimal.TEN)
                .term(12)
                .documentNumber("123456789")
                .build();

        Mono<CreditRequest> result = CreateCreditRequestPayloadValidator.validate(creditRequest);

        StepVerifier.create(result)
                .expectErrorMatches(error -> {
                    BusinessException businessException = (BusinessException) error;
                    return businessException.getMessage().equals(CreditRequestConstants.STATUS_REQUIRED);
                })
                .verify();
    }

    @Test
    void testFailCreditTypeNull() {
        CreditRequest creditRequest = CreditRequest.builder()
                .amount(BigDecimal.TEN)
                .term(12)
                .documentNumber("123456789")
                .status(CreditStatusEnum.PENDING.name())
                .build();

        Mono<CreditRequest> result = CreateCreditRequestPayloadValidator.validate(creditRequest);

        StepVerifier.create(result)
                .expectErrorMatches(error -> {
                    BusinessException businessException = (BusinessException) error;
                    return businessException.getMessage().equals(CreditRequestConstants.CREDIT_TYPE_REQUIRED);
                })
                .verify();
    }
}
