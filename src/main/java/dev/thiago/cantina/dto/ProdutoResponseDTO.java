package dev.thiago.cantina.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        BigDecimal valorVenda,
        Long categoriaId,
        String categoriaNome
) {
}
