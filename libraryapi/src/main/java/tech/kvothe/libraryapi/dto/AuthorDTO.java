package tech.kvothe.libraryapi.dto;

import tech.kvothe.libraryapi.model.Author;

import java.time.LocalDate;

public record AuthorDTO(String name, LocalDate birthday, String nationality) {
    public Author toEntity() {
        return new Author(name, birthday, nationality);
    }
}
