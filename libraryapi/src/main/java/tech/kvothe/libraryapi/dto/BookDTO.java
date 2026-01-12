package tech.kvothe.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;
import tech.kvothe.libraryapi.model.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Book")
public record BookDTO(
        @NotBlank @ISBN String isbn,
        @NotBlank String title,
        @NotNull @Past LocalDate publicationDate,
        @NotNull BookGenre genre,
        BigDecimal price,
        @NotNull
        UUID authorId) {
}
