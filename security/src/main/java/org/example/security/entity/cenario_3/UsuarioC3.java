package org.example.security.entity.cenario_3;

import jakarta.persistence.OneToOne;
import lombok.Data;
import org.example.security.entity.cenario_2.PessoaC2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Data
public class UsuarioC3 implements UserDetails {

    private final PessoaC3 pessoa;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.pessoa.getSenha();
    }

    @Override
    public String getUsername() {
        return this.pessoa.getUsuario();
    }
}
