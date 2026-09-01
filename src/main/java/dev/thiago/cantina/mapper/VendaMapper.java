package dev.thiago.cantina.mapper;

import dev.thiago.cantina.dto.venda.VendaResponseDTO;
import dev.thiago.cantina.entity.Venda;

import java.math.BigDecimal;

public class VendaMapper {

    public static VendaResponseDTO toDTO(Venda venda) {

        BigDecimal valorPago = venda.getPagamentos()
                .stream()
                .map(pagamento -> pagamento.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorPendente = venda.getValorTotal()
                .subtract(valorPago);

        return new VendaResponseDTO(
                venda.getId(),
                venda.getDataHora(),
                venda.getValorTotal(),
                valorPago,
                valorPendente,
                venda.getAluno() != null ? venda.getAluno().getId() : null,
                venda.getItens()
                        .stream()
                        .map(ItemVendaMapper::toDTO)
                        .toList(),
                venda.getPagamentos()
                        .stream()
                        .map(PagamentoMapper::toDTO)
                        .toList(),
                venda.getStatusPagamento()
        );
    }
}
