package org.example.atvsecurity2.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.atvsecurity2.controller.dto.MudancaSenhaDTO;
import org.example.atvsecurity2.entity.Usuario;
import org.example.atvsecurity2.service.UsuarioService;
import org.example.atvsecurity2.utils.CookieUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    CookieUtils cookieUtils;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detalhes")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<?> usuarioLogado(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> buscarTodos(){
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('FUNCIONARIO')")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        usuarioService.atualizarUsuario(usuario, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("senha")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> alterarSenha(@AuthenticationPrincipal Usuario usuario, @RequestBody MudancaSenhaDTO dto){
        if(!usuarioService.alterarSenha(usuario,dto)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuarioReal(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity<?> deletarUsuario(@AuthenticationPrincipal Usuario usuario,HttpServletResponse response) {
        usuarioService.desabilitarUsuario(usuario);
        Cookie cookie = cookieUtils.removerCookie();
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}