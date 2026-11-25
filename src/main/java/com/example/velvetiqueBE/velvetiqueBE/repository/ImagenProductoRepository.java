package com.example.velvetiqueBE.velvetiqueBE.repository;

import com.example.velvetiqueBE.velvetiqueBE.model.ImagenProducto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Long> {

    List<ImagenProducto> findByProducto_IdProductoOrderByOrdenAsc(Long idProducto);
    Optional<ImagenProducto> findFirstByProducto_IdProductoAndEsPrincipalTrue(Long idProducto);
    void deleteByProducto_IdProducto(Long idProducto);
}
