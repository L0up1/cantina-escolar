package dev.thiago.cantina.service;

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
    public Categoria salvar(Categoria categoria) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNomeIgnoreCase(categoria.getNome());

        if (categoriaExistente.isPresent()) {
            throw new CategoriaJaExisteException(categoria.getNome());
        }
        return categoriaRepository.save(categoria);
    }

    public List<CategoriaResponseDTO> listar(){
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(CategoriaMapper::toDTO).toList();
    }
}
