package dev.thiago.cantina.controller;

import dev.thiago.cantina.dto.VendaRequestDTO;
import dev.thiago.cantina.dto.VendaResponseDTO;
import dev.thiago.cantina.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendaResponseDTO salvar(@RequestBody @Valid VendaRequestDTO dto) {
        return vendaService.salvar(dto);

    }

    @GetMapping("/{id}")
    public VendaResponseDTO buscarPorId(@PathVariable Long id) {
        return vendaService.buscarPorId(id);
    }

    @GetMapping
    public List<VendaResponseDTO> listar(){
        return vendaService.listar();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        vendaService.deletar(id);
    }
}
