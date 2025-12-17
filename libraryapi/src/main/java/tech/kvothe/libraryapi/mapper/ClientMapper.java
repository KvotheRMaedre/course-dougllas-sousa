package tech.kvothe.libraryapi.mapper;

import org.mapstruct.Mapper;
import tech.kvothe.libraryapi.dto.ClientDTO;
import tech.kvothe.libraryapi.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO clientDTO);

    ClientDTO toDto(Client client);
}
