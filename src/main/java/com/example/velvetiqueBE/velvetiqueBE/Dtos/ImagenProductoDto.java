package com.example.velvetiqueBE.velvetiqueBE.Dtos;

import com.example.velvetiqueBE.velvetiqueBE.model.ImagenProducto;
import com.example.velvetiqueBE.velvetiqueBE.model.Producto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ImagenProductoDto(
        Long idImagen,
        @NotNull
        Long idProducto,
        @NotBlank
        @Size(max = 500)
        String urlImagen,
        @NotNull
        Boolean esPrincipal,
        @NotNull
        Integer orden
) {
    public static ImagenProductoDto fromEntity(ImagenProducto entity) {
        return new ImagenProductoDto(
                entity.getIdImagen(),
                entity.getProducto() != null ? entity.getProducto().getIdProducto() : null,
                entity.getUrlImagen(),
                entity.getEsPrincipal(),
                entity.getOrden()
        );
    }

    public ImagenProducto toEntity(Producto producto) {
        ImagenProducto entity = new ImagenProducto();
        entity.setIdImagen(this.idImagen);
        entity.setProducto(producto);
        entity.setUrlImagen(this.urlImagen);
        entity.setEsPrincipal(this.esPrincipal);
        entity.setOrden(this.orden);
        return entity;
    }
}