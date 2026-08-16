package dev.thiago.cantina.controller;

import dev.thiago.cantina.dto.ErroResponseDTO;
import dev.thiago.cantina.dto.venda.VendaRequestDTO;
import dev.thiago.cantina.dto.venda.VendaResponseDTO;
import dev.thiago.cantina.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }


    @Operation(
            summary = "Registra a venda.",
            description = "Salva a venda."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Venda registrada com sucesso."
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
            description = "Um ou mais produtos informados não foram encontrados.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendaResponseDTO salvar(@RequestBody @Valid VendaRequestDTO dto) {
        return vendaService.salvar(dto);

    }

    @Operation(
            summary = "Busca a venda por ID.",
            description = "Retorna os dados de uma venda específica."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Venda encontrada com sucesso."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Venda não encontrada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )


    @GetMapping("/periodo")
    public List<VendaResponseDTO> listarTodosEntrePeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return vendaService.listarTodosEntrePeriodo(inicio, fim);
    }

    @GetMapping("/{id}")
    public VendaResponseDTO buscarPorId(@PathVariable Long id) {
        return vendaService.buscarPorId(id);
    }

    @Operation(
            summary = "Lista todas as vendas.",
            description = "Lista todas as vendas realizadas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vendas encontradas com sucesso."
    )
    @GetMapping
    public List<VendaResponseDTO> listar(){
        return vendaService.listar();
    }

    @Operation(
            summary = "Exclui uma venda pelo ID",
            description = "Exclui a venda por completo."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Venda excluída com sucesso."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Venda não encontrada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
            )
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        vendaService.deletar(id);
    }


}
