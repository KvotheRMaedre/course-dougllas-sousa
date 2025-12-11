package tech.kvothe.libraryapi.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorResponseDTO(UUID id, String name, LocalDate birthday, String nationality) {

}
