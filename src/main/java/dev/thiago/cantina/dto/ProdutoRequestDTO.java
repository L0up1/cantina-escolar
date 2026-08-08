package dev.thiago.cantina.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        BigDecimal valorVenda,
        Long categoriaId
) {
}
