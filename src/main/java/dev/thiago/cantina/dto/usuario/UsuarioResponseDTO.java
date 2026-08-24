package dev.thiago.cantina.dto.usuario;

import dev.thiago.cantina.enums.Cargo;

public record UsuarioResponseDTO(Long id,
                                 String nome,
                                 String login,
                                 Cargo cargo) {
}
