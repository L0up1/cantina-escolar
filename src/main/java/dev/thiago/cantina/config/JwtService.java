package dev.thiago.cantina.config;

import dev.thiago.cantina.dto.login.LoginRequestDTO;
import dev.thiago.cantina.entity.Usuario;
import dev.thiago.cantina.exception.UsuarioNaoEncontradoException;
import dev.thiago.cantina.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String gerarToken(Usuario usuario){
        return Jwts.builder()
                .subject(usuario.getLogin())
                .claim("cargo", usuario.getCargo().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extrairLogin(String token) {
        return extrairClaims(token).getSubject();
    }

    private Date extrairExpiracao(String token) {
        return extrairClaims(token).getExpiration();
    }

    private boolean tokenExpirado(String token) {
        return extrairExpiracao(token).before(new Date());
    }

    public boolean tokenValido(String token, UserDetails userDetails) {

        String login = extrairLogin(token);

        return login.equals(userDetails.getUsername())
                && !tokenExpirado(token);
    }
}
