package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DetalleCarritoDto(
        Long idDetalleCarrito,
        @NotNull
        Long idCarrito,
        @NotNull
        Long idProducto,
        @NotNull
        Integer cantidad,
        @NotNull
        BigDecimal precioUnitario,
        BigDecimal subtotal) {
}
