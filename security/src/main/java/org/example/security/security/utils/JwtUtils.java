package org.example.security.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.example.security.entity.Usuario;
import org.example.security.security.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtils {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Value("${security.secret:SenhaForteParaOProjetoTopCareDaMI73}$")
    private String senha;
    public String criarToken(Usuario usuario){
        Instant instanteDaAssinatura = Instant.now();
        Instant instanteDeExpiracao = instanteDaAssinatura.plus(1, ChronoUnit.HOURS);
        String[] authorities = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toArray(String[]::new);


        String jwt = JWT.create().withIssuer("Top Care").
                withIssuedAt(instanteDaAssinatura).withSubject(usuario.getUsuario()).
                withExpiresAt(instanteDeExpiracao)
                .withArrayClaim("authorities", authorities).sign(Algorithm.HMAC256(senha));

    return jwt;
    }

    public Authentication validarToken(String token) {
        String username = JWT.require(Algorithm.HMAC256(senha)).build().verify(token).getSubject();
        Usuario usuario = (Usuario) autenticacaoService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities());
    }
}
