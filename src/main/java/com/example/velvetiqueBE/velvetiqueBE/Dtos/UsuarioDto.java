package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioDto(
        Long idUsuario,
        String run,
        String nombre,
        String apellido,
        String correo,
        LocalDate fechaNacimiento,
        Boolean estado,
        LocalDateTime fechaRegistro,
        Long idRol) {
}
