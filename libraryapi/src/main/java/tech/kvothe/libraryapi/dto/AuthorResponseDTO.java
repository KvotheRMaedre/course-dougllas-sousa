package tech.kvothe.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Author")
public record AuthorResponseDTO(UUID id, String name, LocalDate birthday, String nationality) {

}
