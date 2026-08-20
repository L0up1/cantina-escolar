package dev.thiago.cantina.dto.produto;

import java.math.BigDecimal;

public record ProdutoMaisVendidoResponseDTO(
        Long produtoId,
        String produtoNome,
        Long quantidadeVendida,
        BigDecimal valorTotal
) {
}
