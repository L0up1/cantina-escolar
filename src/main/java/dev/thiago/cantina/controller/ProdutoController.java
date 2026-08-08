package dev.thiago.cantina.controller;

import dev.thiago.cantina.dto.ProdutoRequestDTO;
import dev.thiago.cantina.dto.ProdutoResponseDTO;
import dev.thiago.cantina.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ProdutoResponseDTO salvar(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
        return produtoService.salvar(produtoRequestDTO);
    }

    @GetMapping
    public List<ProdutoResponseDTO> listar(){
        return produtoService.listar();
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id){
        return produtoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
         produtoService.deletar(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO produtoResponseDTO (@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO dto){
        return produtoService.atualizar(id, dto);
    }
}
