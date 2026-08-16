package dev.thiago.cantina.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoriaResponseDTO(
        @Schema(
                description = "Identificador da categoria",
                example = "1"
        )
        Long id,
        @Schema(
                description = "Nome da categoria",
                example = "Doces"
        )
        String nome
) {
}
