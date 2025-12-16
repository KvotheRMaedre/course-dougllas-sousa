package tech.kvothe.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.model.Book;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {
    boolean existsByAuthor(Author author);

    Optional<Book> findByIsbn(String isbn);
}
