package tech.kvothe.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(name = "User")
public record UserDTO(
        @NotBlank String login,
        @NotBlank String password,
        @Email @NotBlank String email,
        List<String> roles) {
}
