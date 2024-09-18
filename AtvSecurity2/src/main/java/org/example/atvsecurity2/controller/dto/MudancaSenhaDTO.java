package org.example.atvsecurity2.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MudancaSenhaDTO {
    @NotEmpty
    private String email;
    @NotEmpty
    private String senhaAtual;
    @NotEmpty
    private String novaSenha;

}
