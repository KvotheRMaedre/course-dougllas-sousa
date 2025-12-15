package tech.kvothe.libraryapi.service;

import org.springframework.stereotype.Service;
import tech.kvothe.libraryapi.model.Book;
import tech.kvothe.libraryapi.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
