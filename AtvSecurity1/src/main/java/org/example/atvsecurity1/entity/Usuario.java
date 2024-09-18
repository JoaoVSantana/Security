package org.example.atvsecurity1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.atvsecurity1.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuario;
    private String email;
    private String senha;
    private String nome;
    private Perfil perfil;
    private boolean habilitado = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(perfil == Perfil.ADMINISTRADOR){
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
}
