package dev.thiago.cantina.mapper;

import dev.thiago.cantina.dto.pagamento.PagamentoResponseDTO;
import dev.thiago.cantina.entity.Pagamento;

public class PagamentoMapper {
    public static PagamentoResponseDTO toDTO(Pagamento pagamento) {
        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getFormaPagamento(),
                pagamento.getDataHora()
        );
    }
}
