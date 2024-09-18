package org.example.atvsecurity2.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
        @NotEmpty
        String usuario,
        @NotEmpty
        String senha) {
}
