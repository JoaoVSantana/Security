package org.example.security.repository;

import org.example.security.entity.Usuario;
import org.example.security.entity.cenario_2.UsuairioC2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    Optional<Usuario> findByEmail(String email);
}
