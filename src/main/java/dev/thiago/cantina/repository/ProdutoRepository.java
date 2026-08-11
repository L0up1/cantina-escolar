package dev.thiago.cantina.repository;

import dev.thiago.cantina.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNomeIgnoreCaseAndCategoriaId(
            String nome,
            Long categoriaId
    );
}
