package tech.kvothe.libraryapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import tech.kvothe.libraryapi.dto.BookDTO;
import tech.kvothe.libraryapi.dto.BookResponseDTO;
import tech.kvothe.libraryapi.model.Book;
import tech.kvothe.libraryapi.repository.AuthorRepository;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(bookDTO.authorId()).orElse(null) )")
    public abstract Book toEntity(BookDTO bookDTO);

    public abstract BookResponseDTO toDto(Book book);
}
