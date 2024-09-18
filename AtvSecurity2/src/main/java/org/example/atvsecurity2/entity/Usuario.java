package org.example.atvsecurity2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String usuario;
    private String nome;
    private String senha;
    private String email;
    private Perfil perfil;
    private boolean habilitado = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(perfil == Perfil.ADMIN){
            return List.of(Perfil.values());
        } else if (perfil == Perfil.FUNCIONARIO){
            return List.of(Perfil.FUNCIONARIO, Perfil.CLIENTE);
        }
        return List.of(Perfil.CLIENTE);
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isEnabled() {
        return this.habilitado;
    }
}
