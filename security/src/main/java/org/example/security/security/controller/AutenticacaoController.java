package org.example.security.security.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.security.entity.Usuario;
import org.example.security.security.controller.dto.LoginDTO;
import org.example.security.security.utils.CookieUtils;
import org.example.security.security.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AutenticacaoController {

    private AuthenticationProvider authenticationProvider;
    private SecurityContextRepository securityContextRepository;
    JwtUtils jwtUtils;
    CookieUtils cookieUtils;

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> usuarioLogado(@AuthenticationPrincipal Usuario usuario){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Usuario usuario = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response){
        Authentication upat = new UsernamePasswordAuthenticationToken
                (loginDTO.usuario(), loginDTO.senha());
        upat = authenticationProvider.authenticate(upat);
        if (upat.isAuthenticated()){
            Usuario usuario = (Usuario) upat.getPrincipal();
            String jwt = jwtUtils.criarToken(usuario);
            Cookie cookieJwt = cookieUtils.criarCookie(jwt);
            response.addCookie(cookieJwt);
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(upat);
//            securityContextRepository.saveContext(context, request, response);
        }
        return ResponseEntity.ok((Usuario) upat.getPrincipal());
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        Cookie cookie = cookieUtils.removerCookie();
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }



}
