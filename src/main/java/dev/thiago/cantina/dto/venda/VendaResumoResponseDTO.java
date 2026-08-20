package dev.thiago.cantina.dto.venda;

import java.math.BigDecimal;

public record VendaResumoResponseDTO(
        Long totalVendas,
        BigDecimal valorTotal,
        BigDecimal valorPago,
        BigDecimal valorPendente
) {
}
