package tech.kvothe.libraryapi.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import tech.kvothe.libraryapi.exception.OperationNotAllowedException;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.repository.AuthorRepository;
import tech.kvothe.libraryapi.validator.AuthorValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final SecurityService securityService;
    private final AuthorValidator authorValidator;

    public AuthorService(AuthorRepository authorRepository, SecurityService securityService, AuthorValidator authorValidator) {
        this.authorRepository = authorRepository;
        this.securityService = securityService;
        this.authorValidator = authorValidator;
    }

    public void save(Author author) {
        authorValidator.validateDuplicates(author);
        author.setUser(securityService.getAuthenticatedUser());
        authorRepository.save(author);
    }

    public Optional<Author> getById(String id) {
        return authorRepository.findById(UUID.fromString(id));
    }

    public void delete(Author author) {
        if (authorValidator.hasBook(author))
            throw new OperationNotAllowedException("Não é permitido deletar um autor que possui livros cadastrados.");

        authorRepository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        return authorRepository.findByNameAndNationality(name, nationality);
    }

    public List<Author> searchByExample(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var authorExample = Example.of(author, exampleMatcher);
        return authorRepository.findAll(authorExample);
    }

    public void update(Author author) {
        authorValidator.validateDuplicates(author);
        authorRepository.save(author);
    }
}
