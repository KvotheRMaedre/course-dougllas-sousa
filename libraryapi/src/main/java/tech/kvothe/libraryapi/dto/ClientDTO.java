package tech.kvothe.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Client")
public record ClientDTO(@NotBlank String clientId,
                        @NotBlank String clientSecret,
                        @NotBlank String redirectURI,
                        String scope) {
}
