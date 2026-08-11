package dev.thiago.cantina.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErroResponseDTO(
        LocalDateTime timestamp,
        int status,
        String erro
) {
}
