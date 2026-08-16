package dev.thiago.cantina.repository;

import dev.thiago.cantina.entity.Aluno;
import dev.thiago.cantina.enums.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByAlunoIgnoreCaseAndTurma(String nome, Turma turma);
}
