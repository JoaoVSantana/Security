package org.example.security.controller;

import lombok.AllArgsConstructor;
import org.example.security.service.UsuarioService;
import org.example.security.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UserController {
    private UsuarioService usuarioService;
    private final String hasAuthority = "hasAuthority('ADMIN')";

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

}
