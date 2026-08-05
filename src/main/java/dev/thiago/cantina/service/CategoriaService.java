package dev.thiago.cantina.service;

import dev.thiago.cantina.dto.CategoriaRequestDTO;
import dev.thiago.cantina.dto.CategoriaResponseDTO;
import dev.thiago.cantina.entity.Categoria;
import dev.thiago.cantina.exception.CategoriaJaExisteException;
import dev.thiago.cantina.exception.CategoriaNaoEncontradaException;
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

    public void excluirCategoria(Long id){
        categoriaRepository.deleteById(id);
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        return CategoriaMapper.toDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(dto.nome()));

        Optional<Categoria> categoriaExistente = categoriaRepository.findByNomeIgnoreCase(dto.nome());
        if (categoriaExistente.isPresent() && !categoriaExistente.get().getId().equals(id)) {
            throw new CategoriaJaExisteException(dto.nome());
        }

        categoria.setNome(dto.nome());

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return CategoriaMapper.toDTO(categoriaAtualizada);
    }
}
