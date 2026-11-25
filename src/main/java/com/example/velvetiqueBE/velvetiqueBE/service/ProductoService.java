package com.example.velvetiqueBE.velvetiqueBE.service;

import com.example.velvetiqueBE.velvetiqueBE.model.ImagenProducto;
import com.example.velvetiqueBE.velvetiqueBE.model.Producto;
import com.example.velvetiqueBE.velvetiqueBE.repository.ImagenProductoRepository;
import com.example.velvetiqueBE.velvetiqueBE.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ImagenProductoRepository imagenProductoRepository;

    public ProductoService(
            ProductoRepository productoRepository,
            ImagenProductoRepository imagenProductoRepository) {
        this.productoRepository = productoRepository;
        this.imagenProductoRepository = imagenProductoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public void deleteById(Long id) {
        List<ImagenProducto> imagenesAsociadas = imagenProductoRepository.findAll().stream()
                .filter(img -> img.getProducto() != null
                        && id.equals(img.getProducto().getIdProducto()))
                .toList();

        if (!imagenesAsociadas.isEmpty()) {
            imagenProductoRepository.deleteAll(imagenesAsociadas);
        }

        productoRepository.deleteById(id);
    }
}