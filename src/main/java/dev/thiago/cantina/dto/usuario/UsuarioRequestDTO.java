package dev.thiago.cantina.dto.usuario;

import dev.thiago.cantina.enums.Cargo;

public record UsuarioRequestDTO(String nome,
                                String login,
                                String senha) {
}
