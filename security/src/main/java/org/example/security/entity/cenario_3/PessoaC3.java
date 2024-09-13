package org.example.security.entity.cenario_3;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.example.security.entity.cenario_2.UsuairioC2;

@Data
@Entity
public class PessoaC3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean temCachorro;
    private String usuario;
    private String senha;
    private String email;


}
