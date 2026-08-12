package dev.thiago.cantina.controller;


import dev.thiago.cantina.dto.CategoriaRequestDTO;
import dev.thiago.cantina.dto.CategoriaResponseDTO;
import dev.thiago.cantina.dto.ErroResponseDTO;
import dev.thiago.cantina.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(
        name = "Categorias",
        description = "Operações relacionadas às categorias da cantina."
)
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(
            summary = "Lista todas as categorias",
            description = "Retorna todas as categorias cadastradas na cantina."
    )
    @GetMapping
    public List<CategoriaResponseDTO> listar(){
        return categoriaService.listar();
    }

    @Operation(
            summary = "Busca uma categoria pelo ID",
            description = "Retorna os dados de uma categoria específica."
    )
    @GetMapping("/{id}")
    public CategoriaResponseDTO buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @Operation(
            summary = "Cadastra uma nova categoria",
            description = "Cria uma nova categoria para a cantina."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Categoria criada com sucesso."
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
            responseCode = "409",
            description = "A categoria informada já existe.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponseDTO salvar(@Valid @RequestBody CategoriaRequestDTO dto) {
            return categoriaService.salvar(dto);
    }


    @Operation(
            summary = "Exclui uma categoria",
            description = "Exclui uma categoria existente pelo ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Categoria excluída com sucesso."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        categoriaService.excluir(id);
    }


    @Operation(
            summary = "Atualiza uma categoria",
            description = "Atualiza o nome de uma categoria existente."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categoria atualizada com sucesso."
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
            description = "Categoria não encontrada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Já existe uma categoria com esse nome.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @PutMapping("/{id}")
    public CategoriaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.atualizar(id, dto);
    }

}
