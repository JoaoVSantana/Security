package org.example.security.service;

import lombok.AllArgsConstructor;
import org.example.security.entity.Usuario;
import org.example.security.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public void criarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
