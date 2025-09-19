package co.com.jcuadrado.api.mapper;

import co.com.jcuadrado.api.dto.request.CreditRequestFilterDTO;
import co.com.jcuadrado.model.creditrequest.CreditRequestFilter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditRequestFilterDtoMapper {
    CreditRequestFilter toModel(CreditRequestFilterDTO creditRequestFilterDTO);
}
