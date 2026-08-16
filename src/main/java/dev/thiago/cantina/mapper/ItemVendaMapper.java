package dev.thiago.cantina.mapper;

import dev.thiago.cantina.dto.item_venda.ItemVendaResponseDTO;
import dev.thiago.cantina.entity.ItemVenda;

public class ItemVendaMapper {

    public static ItemVendaResponseDTO toDTO (ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(
                itemVenda.getId(),
                itemVenda.getProduto().getId(),
                itemVenda.getProduto().getNome(),
                itemVenda.getQuantidade(),
                itemVenda.getValorUnitario(),
                itemVenda.getValorTotal()
        );
    }
}
