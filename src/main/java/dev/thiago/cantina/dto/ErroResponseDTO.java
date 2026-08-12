package dev.thiago.cantina.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErroResponseDTO(
        @Schema(
                description = "Data e hora em que o erro ocorreu",
                example = "2026-08-12T10:30:00"
        )
        LocalDateTime timestamp,
        @Schema(
                description = "Código HTTP do erro",
                example = "404"
        )
        int status,
        @Schema(
                description = "Mensagem descrevendo o erro",
                example = "Produto com ID '10' não encontrado."
        )
        String erro
) {
}
