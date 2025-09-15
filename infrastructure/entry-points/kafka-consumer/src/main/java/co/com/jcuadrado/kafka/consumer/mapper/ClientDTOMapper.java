package co.com.jcuadrado.kafka.consumer.mapper;

import co.com.jcuadrado.kafka.consumer.dto.ClientDTO;
import co.com.jcuadrado.model.client.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientDTOMapper {
    Client toModel(ClientDTO clientDTO);
}
