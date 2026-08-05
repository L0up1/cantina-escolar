package dev.thiago.cantina.controller;


import dev.thiago.cantina.dto.CategoriaRequestDTO;
import dev.thiago.cantina.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("categorias",categoriaService.listar());
        return "categorias";
    }

    @PostMapping
    public String salvar(
            @ModelAttribute CategoriaRequestDTO dto
    ) {

        categoriaService.salvar(dto);

        return "redirect:/categorias";
    }

}
