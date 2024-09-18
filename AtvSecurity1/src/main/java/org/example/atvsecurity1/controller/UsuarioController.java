package org.example.atvsecurity1.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.atvsecurity1.controller.dto.MudancaSenhaDTO;
import org.example.atvsecurity1.entity.Usuario;
import org.example.atvsecurity1.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.criarUsuario(usuario), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('FUNCIONARIO')")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        usuarioService.atualizarUsuario(usuario, id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('CLIENTE')")
    @GetMapping("/detalhes")
    public ResponseEntity<?> buscarUsuario(@AuthenticationPrincipal Usuario usuario) {
        return new ResponseEntity<>(usuarioService.buscarUsuarioLogado(usuario), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> buscarTodos(){
        return new ResponseEntity<>(usuarioService.buscarTodos(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/senha")
    public ResponseEntity<?> alterarSenha(@AuthenticationPrincipal Usuario usuario, @RequestBody MudancaSenhaDTO dto) {
        if(!usuarioService.alterarSenha(usuario, dto)){
            System.out.println("Senha atual incorreta");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        };
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity<?> deletarUsuario(@AuthenticationPrincipal Usuario usuario, HttpServletRequest request, HttpServletResponse response) {
        usuarioService.deletarUsuario(usuario, request, response);
        return ResponseEntity.ok().build();
    }



}
