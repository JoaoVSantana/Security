package org.example.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Perfil implements GrantedAuthority {

    ADMIN("ADMIN"),
    USUARIO("USUARIO");
    private final String nome;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
