package tech.kvothe.libraryapi.validator;

import org.springframework.stereotype.Component;
import tech.kvothe.libraryapi.exception.DuplicatedResourceException;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.repository.AuthorRepository;
import tech.kvothe.libraryapi.repository.BookRepository;

@Component
public class AuthorValidator{

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorValidator(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void validateDuplicates(Author author) {
        if (authorExists(author))
            throw new DuplicatedResourceException("Esse autor já está cadastrado no banco de dados!");
    }

    public boolean authorExists(Author author) {
        var authorOptional = authorRepository.findByNameAndBirthdayAndNationality(
                author.getName(), author.getBirthday(), author.getNationality());

        return authorOptional.isPresent();
    }

    public boolean hasBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
