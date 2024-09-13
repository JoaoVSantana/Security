package org.example.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.security.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
//    private boolean habilitado = true;
//    private boolean senhaExpirada;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(perfil);
    }

//    @Override
//    public  boolean isCredentialsNonExpired() {
//        return !this.senhaExpirada;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.habilitado;
//    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }
}
