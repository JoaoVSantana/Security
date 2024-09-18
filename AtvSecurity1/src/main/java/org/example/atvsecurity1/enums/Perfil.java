package org.example.atvsecurity1.enums;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Perfil implements GrantedAuthority {

    ADMINISTRADOR("Administrador"),
    FUNCIONARIO("Funcionario"),
    CLIENTE("Cliente");
    private final String nome;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
