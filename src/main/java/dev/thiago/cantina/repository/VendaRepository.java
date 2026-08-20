package dev.thiago.cantina.repository;

import dev.thiago.cantina.dto.venda.VendaPorPagamentoResponseDTO;
import dev.thiago.cantina.dto.venda.VendaResumoResponseDTO;
import dev.thiago.cantina.entity.Venda;
import dev.thiago.cantina.enums.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataHoraBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<Venda> findByAlunoIdAndStatusPagamento(
            Long alunoId,
            StatusPagamento statusPagamento
    );

    @Query("""
    SELECT new dev.thiago.cantina.dto.venda.VendaResumoResponseDTO(
        COUNT(v),
        COALESCE(SUM(v.valorTotal), 0),
        COALESCE(SUM(
            CASE
                WHEN v.statusPagamento = dev.thiago.cantina.enums.StatusPagamento.PAGO
                THEN v.valorTotal
                ELSE 0
            END
        ), 0),
        COALESCE(SUM(
            CASE
                WHEN v.statusPagamento = dev.thiago.cantina.enums.StatusPagamento.PENDENTE
                THEN v.valorTotal
                ELSE 0
            END
        ), 0)
    )
    FROM Venda v
    WHERE v.dataHora >= :inicio
      AND v.dataHora < :fim
""")
    VendaResumoResponseDTO buscarResumo(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    @Query("""
    SELECT new dev.thiago.cantina.dto.venda.VendaPorPagamentoResponseDTO(
    COUNT(v),
        COALESCE(SUM(v.valorTotal), 0),
        COALESCE(SUM(
            CASE
                WHEN v.formaPagamento = dev.thiago.cantina.enums.FormaPagamento.PIX
                THEN v.valorTotal
                ELSE 0
            END
        ), 0),
        COALESCE(SUM(
            CASE
                WHEN v.formaPagamento = dev.thiago.cantina.enums.FormaPagamento.DINHEIRO
                THEN v.valorTotal
                ELSE 0
            END
        ), 0),
        COALESCE(SUM(
            CASE
                WHEN v.formaPagamento = dev.thiago.cantina.enums.FormaPagamento.CARTAO
                THEN v.valorTotal
                ELSE 0
            END
        ), 0),
        COALESCE(SUM(
            CASE
                WHEN v.statusPagamento = dev.thiago.cantina.enums.StatusPagamento.PENDENTE
                THEN v.valorTotal
                ELSE 0
            END
        ), 0)
    )
    FROM Venda v
    WHERE v.dataHora >= :inicio
      AND v.dataHora < :fim
""")
    VendaPorPagamentoResponseDTO buscarPorFormaPagamento(
            LocalDateTime inicio,
            LocalDateTime fim
    );




}
