package tech.kvothe.libraryapi.service;

import org.springframework.stereotype.Service;
import tech.kvothe.libraryapi.dto.AuthorDTO;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.repository.AuthorRepository;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author save(AuthorDTO authorDTO) {
        return authorRepository.save(authorDTO.toEntity());
    }
}
