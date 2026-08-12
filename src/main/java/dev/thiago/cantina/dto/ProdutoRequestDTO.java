package dev.thiago.cantina.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @Schema(
                description = "Nome do produto",
                example = "Fini Dentadura"
        )
        @NotBlank(message = "O nome do produto é obrigatório.")
        @Size(
                min = 3,
                max = 50,
                message = "O nome deve ter entre 3 e 50 caracteres."
        )
        String nome,
        @Schema(
                description = "Valor de venda do produto",
                example = "2.00"
        )
        @NotNull(message = "O valor de venda é obrigatório.")
        @Positive(message = "O valor de venda deve ser maior que zero.")
        BigDecimal valorVenda,
        @Schema(
                description = "ID da categoria à qual o produto pertence",
                example = "1"
        )
        @NotNull(message = "A categoria é obrigatória.")
        Long categoriaId
) {
}
