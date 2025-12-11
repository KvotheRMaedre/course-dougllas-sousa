package tech.kvothe.libraryapi.validator;

import org.springframework.stereotype.Component;
import tech.kvothe.libraryapi.exception.DuplicatedResourceException;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.repository.AuthorRepository;

@Component
public class AuthorValidator{

    private final AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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
}
