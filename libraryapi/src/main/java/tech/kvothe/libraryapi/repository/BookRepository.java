package tech.kvothe.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kvothe.libraryapi.model.Book;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
