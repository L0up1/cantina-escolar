package dev.thiago.cantina.dto.venda;

import dev.thiago.cantina.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;

public record PagamentoVendaRequestDTO(
        @NotNull(message = "A forma de pagamento é obrigatória.")
        FormaPagamento formaPagamento
) {
}
