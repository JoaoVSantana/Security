package org.example.atvsecurity2.service;

import lombok.AllArgsConstructor;
import org.example.atvsecurity2.controller.dto.MudancaSenhaDTO;
import org.example.atvsecurity2.entity.Perfil;
import org.example.atvsecurity2.entity.Usuario;
import org.example.atvsecurity2.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public void criarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(Usuario usuario, Long id) {
       Usuario usuarioEncontrado;
        usuarioEncontrado = usuarioRepository.findById(id).get();
        usuarioEncontrado.setUsuario(usuario.getUsuario());
        usuarioEncontrado.setSenha(usuario.getSenha());
        usuarioEncontrado.setNome(usuario.getNome());
        usuarioEncontrado.setEmail(usuario.getEmail());
        usuarioEncontrado.setPerfil(usuario.getPerfil());
        usuarioEncontrado.setHabilitado(usuario.isHabilitado());
        usuarioRepository.save(usuarioEncontrado);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public boolean alterarSenha(Usuario usuario, MudancaSenhaDTO dto){
        if (usuario.getSenha().equals(dto.getSenhaAtual())){
            usuario.setSenha(dto.getNovaSenha());
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public void desabilitarUsuario(Usuario usuario) {
        usuario.setHabilitado(false);
        usuarioRepository.save(usuario);
    }
}
