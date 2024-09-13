package org.example.security.security.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
        @NotEmpty
        String usuario,
        @NotEmpty
        String senha) {
}
