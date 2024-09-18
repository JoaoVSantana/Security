package org.example.atvsecurity1.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.atvsecurity1.entity.Usuario;
import org.example.atvsecurity1.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AutenticacaoService implements UserDetailsService {

    private UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buscarUsuarioPorUsername(username);
    }

    private Usuario buscarUsuarioPorUsername(String username) {
        Optional<Usuario> usuario = repository.findByUsuario(username);
        if (username.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return usuario.get();
    }

    public void logout(SecurityContextRepository securityContextRepository, HttpServletRequest req,
                       HttpServletResponse res) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        securityContextRepository.saveContext(context, req, res);
        Cookie[] cookies = req.getCookies();
        Cookie cookie = Arrays.stream(cookies).findFirst().orElse(null);
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
        } else {
            throw new RuntimeException("Cookie não encontrado");
        }
    }

}
