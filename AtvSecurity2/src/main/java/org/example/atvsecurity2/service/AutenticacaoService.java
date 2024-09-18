package org.example.atvsecurity2.service;



import lombok.AllArgsConstructor;
import org.example.atvsecurity2.entity.Usuario;
import org.example.atvsecurity2.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AutenticacaoService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = tentarBuscarPorUsuario(username);
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = tentarBuscarPorEmail(username);
        }
        try {
            return usuarioOpt.get();
        } catch (NoSuchElementException e){
            throw new UsernameNotFoundException("O usuário'" + username + "'não foi encontrado");
        }
    }
    private Optional<Usuario> tentarBuscarPorUsuario(String username) {
        return usuarioRepository.findByUsuario(username);
    }

    private Optional<Usuario> tentarBuscarPorEmail(String username) {
        return usuarioRepository.findByEmail(username);
    }
}

