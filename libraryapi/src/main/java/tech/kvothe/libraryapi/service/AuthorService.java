package tech.kvothe.libraryapi.service;

import org.springframework.stereotype.Service;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.repository.AuthorRepository;
import tech.kvothe.libraryapi.validator.AuthorValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;

    public AuthorService(AuthorRepository authorRepository, AuthorValidator authorValidator) {
        this.authorRepository = authorRepository;
        this.authorValidator = authorValidator;
    }

    public Author save(Author author) {
        authorValidator.validateDuplicates(author);
        return authorRepository.save(author);
    }

    public Optional<Author> getById(String id) {
        return authorRepository.findById(UUID.fromString(id));
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        return authorRepository.findByNameAndNationality(name, nationality);
    }

    public void update(Author author) {
        authorValidator.validateDuplicates(author);
        authorRepository.save(author);
    }
}
