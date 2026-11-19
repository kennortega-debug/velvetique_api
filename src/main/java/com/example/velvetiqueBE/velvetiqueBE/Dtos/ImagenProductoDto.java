package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImagenProductoDto(
        Long idImagen,
        @NotNull
        Long idProducto,
        @NotBlank
        String urlImagen,
        @NotNull
        Boolean esPrincipal,
        @NotNull
        Integer orden) {
}
