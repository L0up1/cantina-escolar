package dev.thiago.cantina.dto.aluno;

import dev.thiago.cantina.enums.Turma;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlunoRequestDTO(
        @NotBlank(message = "O nome do aluno é obrigatório.")
        @Size(min = 3,
                max = 130,
                message = "O nome deve ter entre 3 e 130 caracteres.")
        String nome,
        @NotNull(message = "A turma do aluno é obrigatória.")
        Turma turma
) {
}
