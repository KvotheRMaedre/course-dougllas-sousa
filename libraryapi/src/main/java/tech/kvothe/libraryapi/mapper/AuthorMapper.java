package tech.kvothe.libraryapi.mapper;

import org.mapstruct.Mapper;
import tech.kvothe.libraryapi.dto.AuthorDTO;
import tech.kvothe.libraryapi.dto.AuthorResponseDTO;
import tech.kvothe.libraryapi.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO authorDTO);

    AuthorResponseDTO toDto(Author author);
}
