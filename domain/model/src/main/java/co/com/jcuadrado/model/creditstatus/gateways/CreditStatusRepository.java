package co.com.jcuadrado.model.creditstatus.gateways;

import co.com.jcuadrado.model.creditstatus.CreditStatus;
import reactor.core.publisher.Mono;

public interface CreditStatusRepository {
    Mono<CreditStatus> getCreditStatusByName(String name);
}
