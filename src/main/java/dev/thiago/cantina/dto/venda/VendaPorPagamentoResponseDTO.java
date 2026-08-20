package dev.thiago.cantina.dto.venda;

import java.math.BigDecimal;

public record VendaPorPagamentoResponseDTO(
        Long totalVendas,
        BigDecimal valorTotal,
        BigDecimal valorPix,
        BigDecimal valorDinheiro,
        BigDecimal valorCartao,
        BigDecimal valorPendente
) {
}
