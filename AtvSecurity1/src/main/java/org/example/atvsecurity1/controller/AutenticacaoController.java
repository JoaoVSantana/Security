package org.example.atvsecurity1.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.atvsecurity1.controller.dto.LoginDTO;
import org.example.atvsecurity1.entity.Usuario;
import org.example.atvsecurity1.service.AutenticacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private AuthenticationProvider authenticationProvider;

    private SecurityContextRepository securityContextRepository;

    private AutenticacaoService autenticacaoService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
        Authentication auth = new UsernamePasswordAuthenticationToken
                (loginDTO.getUsuario(), loginDTO.getSenha());
        auth = authenticationProvider.authenticate(auth);

        if (auth.isAuthenticated()){
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            securityContextRepository.saveContext(context, request, response);
        }
        return ResponseEntity.ok((Usuario) auth.getPrincipal());
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        autenticacaoService.logout(securityContextRepository, req, res);
        return ResponseEntity.ok().build();
    }
}
