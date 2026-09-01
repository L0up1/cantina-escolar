package dev.thiago.cantina.dto.venda;

import dev.thiago.cantina.dto.item_venda.ItemVendaRequestDTO;
import dev.thiago.cantina.dto.pagamento.PagamentoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;


import java.util.List;

public record VendaRequestDTO(
        @NotEmpty(message = "A venda deve possuir pelo menos um item.")
        @Valid
        List<ItemVendaRequestDTO> itens,

        Long alunoId,

        @Valid
        List<PagamentoRequestDTO> pagamentos
) {
}
