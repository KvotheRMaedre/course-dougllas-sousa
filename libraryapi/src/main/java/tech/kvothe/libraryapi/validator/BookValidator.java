package tech.kvothe.libraryapi.validator;

import org.springframework.stereotype.Component;
import tech.kvothe.libraryapi.exception.DuplicatedResourceException;
import tech.kvothe.libraryapi.exception.InvalidFieldException;
import tech.kvothe.libraryapi.model.Book;
import tech.kvothe.libraryapi.repository.BookRepository;

@Component
public class BookValidator {

    private final BookRepository bookRepository;
    private static final int YEAR_REQUIRED_PRICE = 2020;

    public BookValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void validateDuplicates(Book book) {
        if (bookIsbnExists(book))
            throw new DuplicatedResourceException("Esse livro já está cadastrado no banco de dados!");
    }

    public void validate(Book book) {
        if (isRequiredPriceNull(book)) {
            throw new InvalidFieldException("Livros publicados depois de 2020 precisar ter preço cadastrado","price");
        }
    }

    private boolean isRequiredPriceNull(Book book) {
        return book.getPrice() == null && book.getPublicationDate().getYear() >= YEAR_REQUIRED_PRICE;
    }

    public boolean bookIsbnExists(Book book) {
        var bookOptional = bookRepository.findByIsbn(book.getIsbn());
        return bookOptional.isPresent();
    }
}
