package dev.thiago.cantina.repository;

import dev.thiago.cantina.dto.produto.ProdutoMaisVendidoResponseDTO;
import dev.thiago.cantina.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    @NativeQuery("SELECT\n" +
            "    p.id,\n" +
            "    p.nome,\n" +
            "    SUM(iv.quantidade) AS quantidade_vendida,\n" +
            "    SUM(iv.valor_total) AS valor_total\n" +
            "FROM item_venda iv\n" +
            "JOIN produtos p ON p.id = iv.produto_id\n" +
            "JOIN vendas v ON v.id = iv.venda_id\n" +
            "WHERE v.data_hora >= :inicio\n" +
            "  AND v.data_hora < :fim\n" +
            "GROUP BY p.id, p.nome\n" +
            "ORDER BY quantidade_vendida DESC")
    List<ProdutoMaisVendidoResponseDTO> produtoMaisVendido(LocalDateTime inicio, LocalDateTime fim);
}
