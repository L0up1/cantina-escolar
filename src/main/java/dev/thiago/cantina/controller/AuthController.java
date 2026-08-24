package dev.thiago.cantina.controller;

import dev.thiago.cantina.dto.login.LoginRequestDTO;
import dev.thiago.cantina.dto.login.LoginResponseDTO;
import dev.thiago.cantina.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return usuarioService.autenticar(dto);
    }
}
