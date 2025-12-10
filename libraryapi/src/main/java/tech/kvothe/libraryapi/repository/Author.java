package tech.kvothe.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Author extends JpaRepository<Author, UUID> {
}
