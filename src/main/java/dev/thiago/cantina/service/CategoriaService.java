package dev.thiago.cantina.service;

import dev.thiago.cantina.dto.CategoriaRequestDTO;
import dev.thiago.cantina.dto.CategoriaResponseDTO;
import dev.thiago.cantina.entity.Categoria;
import dev.thiago.cantina.exception.CategoriaJaExisteException;
import dev.thiago.cantina.mapper.CategoriaMapper;
import dev.thiago.cantina.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNomeIgnoreCase(dto.nome());

        if (categoriaExistente.isPresent()) {
            throw new CategoriaJaExisteException(dto.nome());
        }
        Categoria categoria = CategoriaMapper.toEntity(dto);

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return CategoriaMapper.toDTO(categoriaSalva);
    }

    public List<CategoriaResponseDTO> listar(){
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(CategoriaMapper::toDTO).toList();
    }
}
