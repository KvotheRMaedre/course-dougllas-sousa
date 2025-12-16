package tech.kvothe.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kvothe.libraryapi.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);

    User findByEmail(String email);
}
