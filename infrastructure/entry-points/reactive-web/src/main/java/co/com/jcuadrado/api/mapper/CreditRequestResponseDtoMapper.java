package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.response.CreditRequestResponseDTO;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface CreditRequestResponseDtoMapper {
    CreditRequestResponseDTO toDto(CreditRequestResponse creditRequestResponse);

    default Mono<CreditRequestResponseDTO> toDtoMono(Mono<CreditRequestResponse> creditRequestResponseMono) {
        return creditRequestResponseMono.map(this::toDto);
    }
}