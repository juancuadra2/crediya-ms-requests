package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.request.CreateCreditRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.jcuadrado.api.dto.request.CreditRequestDTO;
import co.com.jcuadrado.model.creditrequest.CreditRequest;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface CreditRequestDTOMapper {
    
    @Mapping(source = "creditType", target = "type")
    @Mapping(source = "status", target = "status")
    CreditRequestDTO toDto(CreditRequest creditRequest);

    CreditRequest toModel(CreateCreditRequestDTO createCreditRequestDTO);

    default Mono<CreditRequestDTO> toDTOMono(Mono<CreditRequest> creditRequestMono) {
        return creditRequestMono.map(this::toDto);
    }
}