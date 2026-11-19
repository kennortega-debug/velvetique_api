package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UsuarioRequestDto(
        @Size(max = 12)
        String run,
        @NotBlank @Size(max = 100) String nombre,
        @Size(max = 100)
        String apellido,
        @NotBlank @Email @Size(max = 150) String correo,
        @NotBlank @Size(max = 255) String passwordHash,
        LocalDate fechaNacimiento,
        @NotNull Boolean estado,
        @NotNull Long idRol) {
}
