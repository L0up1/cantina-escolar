package dev.thiago.cantina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(
                min = 3,
                max = 50,
                message = "O nome deve ter entre 3 e 50 caracteres."
        )
        String nome,
        @NotNull(message = "O valor de venda é obrigatório.")
        @Positive(message = "O valor de venda deve ser maior que zero.")
        BigDecimal valorVenda,
        @NotNull(message = "A categoria é obrigatória.")
        Long categoriaId
) {
}
