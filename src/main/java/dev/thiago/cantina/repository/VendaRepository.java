package dev.thiago.cantina.repository;

import dev.thiago.cantina.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
