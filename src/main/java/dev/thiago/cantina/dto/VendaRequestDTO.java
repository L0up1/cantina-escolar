package dev.thiago.cantina.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record VendaRequestDTO(
        @NotEmpty(message = "A venda deve possuir pelo menos um item.")
        @Valid
        List<ItemVendaRequestDTO> itens
) {
}
