package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MarcaDto(
        Long idMarca,
        @NotBlank
        String nombre,
        String descripcion,
        @NotNull
        Boolean estado) {
}
