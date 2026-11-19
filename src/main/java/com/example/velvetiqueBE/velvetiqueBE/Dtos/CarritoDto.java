package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import com.example.velvetiqueBE.velvetiqueBE.model.Carrito;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CarritoDto(
        Long idCarrito,
        @NotNull
        Long idUsuario,
        Long idDireccion,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaCompra,
        @NotNull
        BigDecimal total,
        @NotNull
        Carrito.Estado estado,
        String metodoPago,
        @NotNull
        Carrito.EstadoPago estadoPago) {
}
