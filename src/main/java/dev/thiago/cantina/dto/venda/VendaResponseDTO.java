package dev.thiago.cantina.dto.venda;

import dev.thiago.cantina.dto.item_venda.ItemVendaResponseDTO;
import dev.thiago.cantina.dto.pagamento.PagamentoResponseDTO;
import dev.thiago.cantina.enums.FormaPagamento;
import dev.thiago.cantina.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaResponseDTO(
        Long id,
        LocalDateTime dataHora,
        BigDecimal valorTotal,
        BigDecimal valorPago,
        BigDecimal valorPendente,
        Long alunoId,
        List<ItemVendaResponseDTO> itens,
        List<PagamentoResponseDTO> pagamentos,
        StatusPagamento statusPagamento
) {
}
