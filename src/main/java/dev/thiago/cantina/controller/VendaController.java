package dev.thiago.cantina.controller;

import dev.thiago.cantina.dto.ErroResponseDTO;
import dev.thiago.cantina.dto.produto.ProdutoMaisVendidoResponseDTO;
import dev.thiago.cantina.dto.venda.*;
import dev.thiago.cantina.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@Tag(
        name = "Vendas",
        description = "Operações relacionadas às vendas da cantina."
)
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

    @GetMapping("/periodo")
    @Operation(
            summary = "Lista todas as vendas realizadas no período selecionado.",
            description = "Retorna todas as vendas feitas naquele período."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vendas encontradas com sucesso."
    )
    @ApiResponse(
            responseCode = "400",
            description = "A data final não pode ser anterior a data final."
    )
    public List<VendaResponseDTO> listarTodosEntrePeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return vendaService.listarTodosEntrePeriodo(inicio, fim);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca uma venda pelo ID.",
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
            ))
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

    @GetMapping("/{id}/pendentes")
    @Operation(
            summary = "Lista todas as vendas pendentes por aluno.",
            description = "Retorna todas as vendas com status pendente de um determinado aluno."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vendas pendentes encontradas com sucesso."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aluno não encontrado.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroResponseDTO.class)
    ))
    public List<VendaResponseDTO> listarVendasPendentesPorId(@PathVariable Long id){
        return vendaService.listarVendasPendentesPorId(id);
    }

    @PutMapping("/{id}/pagar")
    @Operation(
            summary = "Realiza o pagamento das vendas pendentes de um aluno.",
            description = "Altera todas as vendas pendentes do aluno para PAGO e registra a forma de pagamento."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vendas pendentes pagas com sucesso."
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados do pagamento inválidos.",
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
    public List<VendaResponseDTO> atualizarPagamento(@PathVariable Long id, @RequestBody @Valid PagamentoVendaRequestDTO dto) {
        return vendaService.atualizarPagamento(id, dto);
    }

    @GetMapping("/resumo")
    @Operation(
            summary = "Retorna o total resumido de todas as vendas realizadas no período escolhido",
            description = "Retorna os valores totais de todas as vendas realizadas no período escolhido, sendo ela paga ou pendente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vendas resumidas encontradas com sucesso."
    )
    public VendaResumoResponseDTO vendasResumidas(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return vendaService.buscarResusmo(inicio, fim);
    }

    @GetMapping("/relatorio/forma-pagamentos")
    @Operation(
            summary = "Retorna o total resumido de todas as vendas realizadas no período escolhido por forma de pagamento",
            description = "Retorna os valores totais de todas as vendas realizadas no período escolhido por forma de pagamento"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vendas por forma de pagamento encontradas com sucesso."
    )
    public VendaPorPagamentoResponseDTO vendasPorPagamento(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return vendaService.resumoPorPagamento(inicio, fim);
    }

    @GetMapping("/relatorios/produtos")
    public List<ProdutoMaisVendidoResponseDTO> produtoMaisVendido(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return vendaService.produtoMaisVendido(inicio, fim);
    }
}
