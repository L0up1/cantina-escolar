package dev.thiago.cantina.controller;


import dev.thiago.cantina.dto.CategoriaRequestDTO;
import dev.thiago.cantina.dto.CategoriaResponseDTO;
import dev.thiago.cantina.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("categoria", new CategoriaRequestDTO(""));
        model.addAttribute("categoriaId", null);
        model.addAttribute("categorias",categoriaService.listar());
        return "categorias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        CategoriaResponseDTO categoriaResponse = categoriaService.buscarPorId(id);

        CategoriaRequestDTO categoria = new CategoriaRequestDTO(categoriaResponse.nome());

        model.addAttribute("categoria", categoria);
        model.addAttribute("categoriaId", id);
        model.addAttribute("categorias", categoriaService.listar());
        return "categorias";
    }

    @PostMapping
    public String salvar(
            @Valid
            @ModelAttribute CategoriaRequestDTO dto,
            BindingResult result,

            Model model
    ) {

        if(result.hasErrors()){

            model.addAttribute("categorias", categoriaService.listar());

            return "categorias";
        }

        categoriaService.salvar(dto);

        return "redirect:/categorias";
    }


    @DeleteMapping("/{id}")
    public String excluirCategoria(@PathVariable Long id){
        categoriaService.excluirCategoria(id);
        return "redirect:/categorias";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute CategoriaRequestDTO dto) {
        categoriaService.atualizar(id, dto);
        return "redirect:/categorias";
    }

}
