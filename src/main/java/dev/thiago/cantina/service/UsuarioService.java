package dev.thiago.cantina.service;

import dev.thiago.cantina.config.JwtService;
import dev.thiago.cantina.dto.login.LoginRequestDTO;
import dev.thiago.cantina.dto.login.LoginResponseDTO;
import dev.thiago.cantina.dto.usuario.AlterarSenhaRequestDTO;
import dev.thiago.cantina.dto.usuario.UsuarioRequestDTO;
import dev.thiago.cantina.dto.usuario.UsuarioResponseDTO;
import dev.thiago.cantina.dto.usuario.UsuarioUpdateDTO;
import dev.thiago.cantina.entity.Usuario;
import dev.thiago.cantina.enums.Cargo;
import dev.thiago.cantina.exception.UsuarioInvalidoException;
import dev.thiago.cantina.exception.UsuarioJaExisteException;
import dev.thiago.cantina.exception.UsuarioNaoEncontradoException;
import dev.thiago.cantina.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO autenticar(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.login(),
                        dto.senha()
                )
        );

        Usuario usuario = usuarioRepository.findByLogin(dto.login())
                .orElseThrow(() -> new BadCredentialsException("Login ou senha inválidos."));

        String token = jwtService.gerarToken(usuario);
        return new LoginResponseDTO(
                token,
                usuario.getNome(),
                usuario.getCargo()
        );
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setCargo(Cargo.OPERADOR);

        if (usuarioRepository.findByLogin(dto.login()).isPresent()) {
            throw new UsuarioJaExisteException(
                    "Já existe um usuário com o login '" + dto.login() + "'."
            );
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getLogin(),
                usuarioSalvo.getCargo()
        );
    }

    public void alterarSenha(String login, AlterarSenhaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Login ou senha invalidos."));

        boolean senhaAtualCorreta = passwordEncoder.matches(
                dto.senhaAtual(),
                usuario.getSenha()
        );

        if (!senhaAtualCorreta) {
            throw new BadCredentialsException("Senha atual inválida.");
        }

        usuario.setSenha(
                passwordEncoder.encode(dto.novaSenha())
        );

        usuarioRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getLogin(),
                        usuario.getCargo()
                ))
                .toList();
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado."));

        if (usuario.getCargo() == Cargo.ADMIN){
            throw new UsuarioInvalidoException("O administrador principal não pode ser alterado por esta operaçãoo");
        }

        Optional<Usuario> usuarioComMesmoLogin =
                usuarioRepository.findByLogin(dto.login());

        if (usuarioComMesmoLogin.isPresent()
                && !usuarioComMesmoLogin.get().getId().equals(id)) {

            throw new UsuarioJaExisteException(
                    "Já existe um usuário com o login '" + dto.login() + "'."
            );
        }

        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());

        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(
                    passwordEncoder.encode(dto.senha())
            );
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getLogin(),
                usuarioSalvo.getCargo()
        );
    }

    public void excluirUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Usuário não encontrado.")
                );

        if (usuario.getCargo() == Cargo.ADMIN) {
            throw new UsuarioInvalidoException(
                    "O administrador principal não pode ser excluído."
            );
        }

        usuarioRepository.delete(usuario);
    }
}
