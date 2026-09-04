package dev.thiago.cantina.repository;

import dev.thiago.cantina.dto.venda.VendaPorPagamentoResponseDTO;
import dev.thiago.cantina.dto.venda.VendaResumoResponseDTO;
import dev.thiago.cantina.entity.Venda;
import dev.thiago.cantina.enums.StatusPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByDataHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    Page<Venda> findByDataHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable pageable
    );

    List<Venda> findByAlunoIdAndStatusPagamento(
            Long alunoId,
            StatusPagamento statusPagamento
    );





}
