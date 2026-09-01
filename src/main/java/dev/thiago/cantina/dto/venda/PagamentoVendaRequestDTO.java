package dev.thiago.cantina.dto.venda;

import dev.thiago.cantina.dto.pagamento.PagamentoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;


import java.util.List;

public record PagamentoVendaRequestDTO(
        @NotEmpty(message = "Informe pelo menos um pagamento.")
        @Valid
        List<PagamentoRequestDTO> pagamentos
) {
}
