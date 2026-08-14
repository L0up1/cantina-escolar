package dev.thiago.cantina.dto;

import java.math.BigDecimal;

public record ItemVendaResponseDTO(
        Long id,
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        BigDecimal valorUnitario,
        BigDecimal valorTotal
) {
}
