package co.com.jcuadrado.model.credittype.gateways;

import co.com.jcuadrado.model.credittype.CreditType;
import reactor.core.publisher.Mono;

public interface CreditTypeRepository {
    Mono<CreditType> getCreditTypeByName(String name);
}
