package dev.thiago.cantina.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @Schema(
                description = "Nome da categoria",
                example = "Doces"
        )
        @NotBlank(message = "O nome da categoria é obrigatório.")
        @Size(
                min = 3,
                max = 50,
                message = "O nome deve ter entre 3 e 50 caracteres."
        )
        String nome
) {
}
