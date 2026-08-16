package dev.thiago.cantina.dto.produto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        @Schema(
                description = "Identificador único do produto",
                example = "2"
        )
        Long id,
        @Schema(
                description = "Nome do produto",
                example = "Fini Dentadura"
        )
        String nome,
        @Schema(
                description = "Valor de venda do produto",
                example = "2.00"
        )
        BigDecimal valorVenda,
        @Schema(
                description = "Identificador da categoria do produto",
                example = "1"
        )
        Long categoriaId,
        @Schema(
                description = "Nome da categoria do produto",
                example = "Doces"
        )
        String categoriaNome
) {
}
