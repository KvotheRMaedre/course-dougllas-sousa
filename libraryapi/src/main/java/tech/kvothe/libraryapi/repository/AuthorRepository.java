package tech.kvothe.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.kvothe.libraryapi.model.Author;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query("SELECT a FROM Author a WHERE (:name is null or a.name = :name) and (:nationality is null"
            + " or a.nationality = :nationality)")
    List<Author> findByNameAndNationality(@Param("name") String name, @Param("nationality") String nationality);
}
