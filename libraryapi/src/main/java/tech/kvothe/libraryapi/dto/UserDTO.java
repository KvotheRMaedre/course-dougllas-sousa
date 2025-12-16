package tech.kvothe.libraryapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDTO(
        @NotBlank String login,
        @NotBlank String password,
        @Email @NotBlank String email,
        List<String> roles) {
}
