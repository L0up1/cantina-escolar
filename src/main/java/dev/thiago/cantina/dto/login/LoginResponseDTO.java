package dev.thiago.cantina.dto.login;

import dev.thiago.cantina.enums.Cargo;

public record LoginResponseDTO(
        String token,
        String nome,
        Cargo cargo
) {
}
