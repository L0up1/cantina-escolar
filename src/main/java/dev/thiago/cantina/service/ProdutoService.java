package dev.thiago.cantina.service;

import dev.thiago.cantina.dto.ProdutoRequestDTO;
import dev.thiago.cantina.dto.ProdutoResponseDTO;
import dev.thiago.cantina.entity.Categoria;
import dev.thiago.cantina.entity.Produto;
import dev.thiago.cantina.exception.CategoriaNaoEncontradaException;
import dev.thiago.cantina.exception.ProdutoJaExisteException;
import dev.thiago.cantina.exception.ProdutoNaoEncontradoException;
import dev.thiago.cantina.mapper.ProdutoMapper;
import dev.thiago.cantina.repository.CategoriaRepository;
import dev.thiago.cantina.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada"));

        produtoRepository
                .findByNomeIgnoreCaseAndCategoriaId(dto.nome(), dto.categoriaId())
                .ifPresent(produto -> {
                    throw new ProdutoJaExisteException("Produto com o nome '" + dto.nome() + "' já existe nessa categoria.");
                });

        Produto produto = ProdutoMapper.toEntity(dto);

        produto.setCategoria(categoria);

        Produto produtoSalvo = produtoRepository.save(produto);

        return ProdutoMapper.toDTO(produtoSalvo);
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID '" + id + "' não encontrado."));
        return ProdutoMapper.toDTO(produto);
    }

    public List<ProdutoResponseDTO> listar(){
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(ProdutoMapper::toDTO).toList();
    }

    @Transactional
    public void deletar(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID '" + id + "' não encontrado."));
        produtoRepository.delete(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID '" + id + "' não encontrado."));

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria com ID '" + dto.categoriaId() + "' não encontrada."));

        produtoRepository
                .findByNomeIgnoreCaseAndCategoriaId(dto.nome(), dto.categoriaId()).
                ifPresent(produtoExistente -> {
                            if (!produtoExistente.getId().equals(id)) {
                                throw new ProdutoJaExisteException(
                                        "Produto com o nome '" + dto.nome()
                                                + "' já existe nesta categoria."
                                );
                            }
                        });
        produto.setNome(dto.nome());
        produto.setValorVenda(dto.valorVenda());
        produto.setCategoria(categoria);

        Produto produtoAtualizado = produtoRepository.save(produto);

        return ProdutoMapper.toDTO(produtoAtualizado);
    }


}
