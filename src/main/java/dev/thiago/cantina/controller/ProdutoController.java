package dev.thiago.cantina.controller;

import dev.thiago.cantina.dto.ErroResponseDTO;
import dev.thiago.cantina.dto.ProdutoRequestDTO;
import dev.thiago.cantina.dto.ProdutoResponseDTO;
import dev.thiago.cantina.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(
            summary = "Cadastra um novo produto",
            description = "Cadastra um produto e associa o produto a uma categoria existente."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Produto cadastrado com sucesso."
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados fornecidos são inválidos.",
            content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErroResponseDTO.class)

    ))
    @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
    ))
    @ApiResponse(
            responseCode = "409",
            description = "Já existe um produto com esse nome nessa categoria.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
    ))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO salvar(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
        return produtoService.salvar(produtoRequestDTO);
    }

    @Operation(
            summary = "Lista todos os produtos",
            description = "Retorna todos os produtos cadastrados na cantina."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produtos encontrados com sucesso."
    )
    @GetMapping
    public List<ProdutoResponseDTO> listar(){
        return produtoService.listar();
    }

    @Operation(
            summary = "Busca um produto pelo ID",
            description = "Retorna os dados de um produto específico."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produto encontrado com sucesso."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id){
        return produtoService.buscarPorId(id);
    }

    @Operation(
            summary = "Exclui um produto pelo ID",
            description = "Exclui um produto que não esteja vinculado a vendas."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Produto excluído com sucesso."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
         produtoService.deletar(id);}

    @Operation(
            summary = "Atualiza um produto",
            description = "Atualiza os dados de um produto existente e permite alterar sua categoria."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso."
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados fornecidos são inválidos.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Produto ou categoria não encontrada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Já existe um produto com esse nome nessa categoria.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar (@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO dto){
        return produtoService.atualizar(id, dto);
    }
}
