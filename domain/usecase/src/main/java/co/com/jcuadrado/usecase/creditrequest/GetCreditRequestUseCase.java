package co.com.jcuadrado.usecase.creditrequest;

import co.com.jcuadrado.constant.AuthConstant;
import co.com.jcuadrado.constant.ErrorCode;
import co.com.jcuadrado.constant.RoleEnum;
import co.com.jcuadrado.exception.BusinessException;
import co.com.jcuadrado.model.auth.AuthInfo;
import co.com.jcuadrado.model.common.PageResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequestFilter;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import co.com.jcuadrado.model.creditrequest.gateways.CreditRequestRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record GetCreditRequestUseCase(CreditRequestRepository creditRequestRepository) {

    public Mono<PageResponse<CreditRequestResponse>> getCreditRequests(CreditRequestFilter filter, AuthInfo authInfo) {
        if (!authInfo.getRole().equals(RoleEnum.ADVISER.name())) {
            return Mono.error(new BusinessException(AuthConstant.ACCESS_DENIED, ErrorCode.FORBIDDEN));
        }
        return creditRequestRepository.findCreditRequests(filter)
                .map(page -> {
                    if (page.getContent() != null) {
                        page.getContent().forEach(cr -> {
                            BigDecimal monthly = calculateMonthlyPayment(cr.getAmount(), cr.getInterestRate(), cr.getTerm());
                            cr.setMonthlyPayment(monthly);
                        });
                    }
                    return page;
                });

    }

    private static BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal monthlyInterestRatePercent, Integer term) {
        if (amount == null || monthlyInterestRatePercent == null || term == null || term <= 0) {
            return BigDecimal.ZERO;
        }

        double monthlyRate = monthlyInterestRatePercent.doubleValue() / 100.0;

        if (monthlyRate == 0) {
            return amount.divide(BigDecimal.valueOf(term), 2, RoundingMode.HALF_UP);
        }

        double denominator = 1.0 - Math.pow(1.0 + monthlyRate, -term);
        double annuityFactor = monthlyRate / denominator;

        BigDecimal payment = amount.multiply(BigDecimal.valueOf(annuityFactor));

        return payment.setScale(2, RoundingMode.HALF_UP);
    }

}