package dev.thiago.cantina.dto;

import java.time.LocalDate;

public record ErroResponseDTO(
        LocalDate timestamp,
        int status,
        String erro
) {
}
