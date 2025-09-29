package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.response.CreditRequestResponseDTO;
import co.com.jcuadrado.api.dto.response.PageResponseDTO;
import co.com.jcuadrado.model.common.PageResponse;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring", uses = CreditRequestResponseDTOMapper.class)
public interface PageResponseDTOMapper {
    PageResponseDTO<CreditRequestResponseDTO> toDto(PageResponse<CreditRequestResponse> pageResponse);

    default Mono<PageResponseDTO<CreditRequestResponseDTO>> toDTOMono(Mono<PageResponse<CreditRequestResponse>> pageResponseMono) {
        return pageResponseMono.map(this::toDto);
    }
}