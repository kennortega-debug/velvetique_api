package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import jakarta.validation.constraints.NotBlank;

public record RolDto(
        Long idRol,
        @NotBlank
        String nombreRol,
        String descripcion) {
}
