package dev.thiago.cantina.repository;

import dev.thiago.cantina.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
