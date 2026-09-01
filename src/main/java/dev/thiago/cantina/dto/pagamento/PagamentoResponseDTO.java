package dev.thiago.cantina.dto.pagamento;

import dev.thiago.cantina.enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponseDTO(Long id,
                                   BigDecimal valor,
                                   FormaPagamento formaPagamento,
                                   LocalDateTime dataHora) {
}
