package org.example.atvsecurity2.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Perfil implements GrantedAuthority {

    ADMIN("Administrador"),
    FUNCIONARIO("Funcionario"),
    CLIENTE("Cliente");
    private final String nome;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
