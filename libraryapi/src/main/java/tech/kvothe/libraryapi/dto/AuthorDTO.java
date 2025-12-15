package tech.kvothe.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import tech.kvothe.libraryapi.model.Author;

import java.time.LocalDate;

public record AuthorDTO(@NotBlank @Size(min = 2, max = 100) String name,
                        @NotNull @Past LocalDate birthday,
                        @NotBlank @Size(min = 2, max = 50)String nationality) {
}
