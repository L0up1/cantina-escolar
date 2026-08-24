package dev.thiago.cantina.controller;

import dev.thiago.cantina.dto.usuario.AlterarSenhaRequestDTO;
import dev.thiago.cantina.dto.usuario.UsuarioRequestDTO;
import dev.thiago.cantina.dto.usuario.UsuarioResponseDTO;
import dev.thiago.cantina.dto.usuario.UsuarioUpdateDTO;
import dev.thiago.cantina.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO cadastrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        return usuarioService.cadastrarUsuario(dto);
    }

    @PutMapping("/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(Authentication authentication,
                             @RequestBody @Valid AlterarSenhaRequestDTO dto){
        String login = authentication.getName();

        usuarioService.alterarSenha(login, dto);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar(){
        return usuarioService.listarUsuarios();
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioUpdateDTO dto
    ) {
        return usuarioService.atualizarUsuario(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
    }
}
