package dev.thiago.cantina.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemVendaRequestDTO(
        @NotNull(message = "O produto é obrigatória.")
        Long produtoId,
        @NotNull(message = "É necessario informar a quantidade.")
        @Positive(message = "A quantidade deve ser maior do que zero.")
        Integer quantidade
) {
}
