package tech.kvothe.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.kvothe.libraryapi.model.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Book")
public record BookResponseDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGenre genre,
        BigDecimal price,
        AuthorResponseDTO author) {
}
