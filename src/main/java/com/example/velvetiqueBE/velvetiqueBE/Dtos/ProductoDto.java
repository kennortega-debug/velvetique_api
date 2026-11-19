package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductoDto(
        Long idProducto,
        @NotBlank
        String codigoSku,
        @NotBlank
        String nombre,
        String descripcion,
        @NotNull
        BigDecimal precio,
        @NotNull
        Integer stock,
        @NotNull
        Integer stockCritico,
        @NotNull
        Boolean visible,
        @NotNull
        Long idCategoria,
        @NotNull
        Long idMarca) {
}
