package dev.thiago.cantina.mapper;

import dev.thiago.cantina.dto.venda.VendaResponseDTO;
import dev.thiago.cantina.entity.Venda;

public class VendaMapper {

    public static VendaResponseDTO toDTO (Venda venda) {
        return new VendaResponseDTO(
                venda.getId(),
                venda.getDataHora(),
                venda.getValorTotal(),
                venda.getAluno() != null ? venda.getAluno().getId() : null,
                venda.getItens()
                        .stream().map(ItemVendaMapper::toDTO)
                        .toList(),
                venda.getFormaPagamento(),
                venda.getStatusPagamento()
        );
    }
}
