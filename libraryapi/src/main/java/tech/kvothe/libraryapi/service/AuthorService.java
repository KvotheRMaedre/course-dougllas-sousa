package tech.kvothe.libraryapi.service;

import org.springframework.stereotype.Service;
import tech.kvothe.libraryapi.dto.AuthorDTO;
import tech.kvothe.libraryapi.model.Author;
import tech.kvothe.libraryapi.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author save(AuthorDTO authorDTO) {
        return authorRepository.save(authorDTO.toEntity());
    }

    public Optional<Author> getById(String id) {
        return authorRepository.findById(UUID.fromString(id));
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        return authorRepository.findByNameAndNationality(name, nationality);
    }
}
