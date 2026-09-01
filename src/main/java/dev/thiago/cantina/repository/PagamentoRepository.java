package dev.thiago.cantina.repository;

import dev.thiago.cantina.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByDataHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
