package dev.thiago.cantina.dto.venda;

import dev.thiago.cantina.dto.item_venda.ItemVendaResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaResponseDTO(
        Long id,
        LocalDateTime dataHora,
        BigDecimal valorTotal,
        List<ItemVendaResponseDTO> itens
) {
}
