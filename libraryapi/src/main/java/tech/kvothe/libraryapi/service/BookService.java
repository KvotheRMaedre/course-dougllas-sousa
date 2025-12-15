package tech.kvothe.libraryapi.service;

import org.springframework.stereotype.Service;
import tech.kvothe.libraryapi.model.Book;
import tech.kvothe.libraryapi.repository.BookRepository;

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
}
