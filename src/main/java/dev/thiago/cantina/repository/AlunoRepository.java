package dev.thiago.cantina.repository;

import dev.thiago.cantina.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
