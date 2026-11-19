package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DireccionDto(
        Long idDireccion,
        @NotNull
        Long idUsuario,
        @NotBlank
        String region,
        @NotBlank
        String comuna,
        @NotBlank
        String calle,
        @NotBlank
        String numero,
        String complemento,
        @NotNull
        Boolean esPrincipal) {
}
