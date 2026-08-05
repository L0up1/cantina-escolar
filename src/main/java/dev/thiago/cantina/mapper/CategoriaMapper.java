package dev.thiago.cantina.mapper;

import dev.thiago.cantina.dto.CategoriaRequestDTO;
import dev.thiago.cantina.dto.CategoriaResponseDTO;
import dev.thiago.cantina.entity.Categoria;

public final class CategoriaMapper {

    private CategoriaMapper() {
    }

    public static Categoria toEntity(CategoriaRequestDTO dto){
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        return categoria;
    }






    public static CategoriaResponseDTO toDTO(Categoria categoria){
        return new CategoriaResponseDTO(categoria.getId(),
                categoria.getNome());
    }
}
