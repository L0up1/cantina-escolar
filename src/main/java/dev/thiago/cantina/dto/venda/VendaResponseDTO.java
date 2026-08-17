package dev.thiago.cantina.dto.venda;

import dev.thiago.cantina.dto.item_venda.ItemVendaResponseDTO;
import dev.thiago.cantina.enums.FormaPagamento;
import dev.thiago.cantina.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaResponseDTO(
        Long id,
        LocalDateTime dataHora,
        BigDecimal valorTotal,
        Long aluno_id,
        List<ItemVendaResponseDTO> itens,
        FormaPagamento formaPagamento,
        StatusPagamento statusPagamento
) {
}
