package org.example.atvsecurity1.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.atvsecurity1.controller.dto.MudancaSenhaDTO;
import org.example.atvsecurity1.entity.Usuario;
import org.example.atvsecurity1.repository.UsuarioRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private AutenticacaoService autenticacaoService;

    private SecurityContextRepository securityContextRepository;

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    private Usuario buscarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return usuario.get();
    }

    public Usuario buscarUsuarioLogado(Usuario usuario) {
        return usuario;
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

    public void deletarUsuario(Usuario usuario, HttpServletRequest request, HttpServletResponse response) {
        autenticacaoService.logout(securityContextRepository, request, response);
        usuarioRepository.delete(usuario);
    }
}
