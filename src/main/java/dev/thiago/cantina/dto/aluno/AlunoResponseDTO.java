package dev.thiago.cantina.dto.aluno;

import dev.thiago.cantina.enums.Turma;

public record AlunoResponseDTO(
        Long id,
        String nome,
        Turma turma
) {
}
