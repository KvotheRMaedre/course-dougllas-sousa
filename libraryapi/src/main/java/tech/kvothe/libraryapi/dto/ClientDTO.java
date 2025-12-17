package tech.kvothe.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientDTO(@NotBlank String clientId,
                        @NotBlank String clientSecret,
                        @NotBlank String redirectURI,
                        String scope) {
}
