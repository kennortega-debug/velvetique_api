package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import com.example.velvetiqueBE.velvetiqueBE.model.MensajeContacto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record MensajeContactoDto(
        Long idMensaje,
        @NotBlank
        String nombre,
        @NotBlank
        String correo,
        @NotBlank
        String asunto,
        @NotBlank
        String mensaje,
        LocalDateTime fechaEnvio,
        @NotNull
        MensajeContacto.Estado estado) {
}
