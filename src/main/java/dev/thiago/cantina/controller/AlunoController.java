package dev.thiago.cantina.controller;

import dev.thiago.cantina.dto.ErroResponseDTO;
import dev.thiago.cantina.dto.aluno.AlunoRequestDTO;
import dev.thiago.cantina.dto.aluno.AlunoResponseDTO;
import dev.thiago.cantina.service.AlunoService;
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
@RequestMapping("/alunos")
@Tag(
        name = "Alunos",
        description = "Operações relacionadas aos alunos da cantina."
)
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastra um novo aluno",
            description = "Cria um novo aluno para a cantina."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Aluno criado com sucesso."
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
            description = "Já existe um aluno com esse nome na turma selecionada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    public AlunoResponseDTO salvar(@RequestBody @Valid AlunoRequestDTO dto) {
        return alunoService.salvar(dto);
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os alunos",
            description = "Retorna todos os alunos cadastrados na cantina."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Alunos encontrados com sucesso."
    )
    public List<AlunoResponseDTO> listar() {
        return alunoService.listar();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um aluno pelo ID",
            description = "Retorna os dados de um aluno específico."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aluno com id fornecido não encontrado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Aluno encontrado com sucesso."
    )
    public AlunoResponseDTO buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Exclui um aluno",
            description = "Exclui um aluno existente pelo ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Aluno excluído com sucesso."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aluno com id fornecido não encontrado.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    public void deletar(@PathVariable Long id) {
        alunoService.deletar(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um aluno",
            description = "Atualiza o nome ou turma de um aluno existente."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Aluno atualizado com sucesso."
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
            description = "Aluno não encontrado.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Já existe um aluno com esse nome na turma selecionada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    public AlunoResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid AlunoRequestDTO dto) {
        return alunoService.atualizar(id, dto);
    }
}
