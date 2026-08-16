package dev.thiago.cantina.mapper;

import dev.thiago.cantina.dto.aluno.AlunoRequestDTO;
import dev.thiago.cantina.dto.aluno.AlunoResponseDTO;
import dev.thiago.cantina.entity.Aluno;

public class AlunoMapper {
    public static Aluno toEntity(AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setTurma(dto.turma());

        return aluno;
    }

    public static AlunoResponseDTO toDTO(Aluno aluno) {
        return new AlunoResponseDTO(aluno.getId(),
                aluno.getNome(),
                aluno.getTurma());
    }
}
