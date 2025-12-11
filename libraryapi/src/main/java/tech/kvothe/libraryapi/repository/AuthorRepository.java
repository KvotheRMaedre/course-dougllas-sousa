package tech.kvothe.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kvothe.libraryapi.model.Author;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
