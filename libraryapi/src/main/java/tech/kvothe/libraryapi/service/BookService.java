package tech.kvothe.libraryapi.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.kvothe.libraryapi.model.Book;
import tech.kvothe.libraryapi.model.BookGenre;
import tech.kvothe.libraryapi.repository.BookRepository;
import tech.kvothe.libraryapi.repository.specs.BookSpecs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getById(UUID id) {
        return bookRepository.findById(id);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public List<Book> search(String isbn, String title, String authorName, BookGenre genre, Integer publicationYear) {
        Specification<Book> specs = Specification
                .where(((root, query, builder) -> builder.conjunction()));

        if (isbn != null)
            specs = specs.and(BookSpecs.isbnEqual(isbn));

        if (title != null)
            specs = specs.and(BookSpecs.titleLike(title));

        if (publicationYear != null)
            specs = specs.and(BookSpecs.publicationYearEqual(publicationYear));

        if (genre != null)
            specs = specs.and(BookSpecs.genreEqual(genre));

        if (authorName != null)
            specs = specs.and(BookSpecs.authorNameLike(authorName));

        return bookRepository.findAll(specs);
    }
}
