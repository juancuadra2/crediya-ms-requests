package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.response.CreditRequestResponseDTO;
import co.com.jcuadrado.model.creditrequest.CreditRequestResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditRequestResponseDTOMapper {
    CreditRequestResponseDTO toDto(CreditRequestResponse creditRequestResponse);
}