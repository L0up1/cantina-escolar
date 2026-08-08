package dev.thiago.cantina.mapper;

import dev.thiago.cantina.dto.ProdutoRequestDTO;
import dev.thiago.cantina.dto.ProdutoResponseDTO;
import dev.thiago.cantina.entity.Produto;

public class ProdutoMapper {
    public static Produto toEntity (ProdutoRequestDTO dto){
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setValorVenda(dto.valorVenda());
        return produto;
    }

    public static ProdutoResponseDTO toDTO (Produto produto){
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getValorVenda(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome()
        );
    }
}
