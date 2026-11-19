package com.example.velvetiqueBE.velvetiqueBE.service;

import com.example.velvetiqueBE.velvetiqueBE.model.ImagenProducto;
import com.example.velvetiqueBE.velvetiqueBE.repository.ImagenProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ImagenProductoService {

    private final ImagenProductoRepository imagenProductoRepository;

    public ImagenProductoService(ImagenProductoRepository imagenProductoRepository) {
        this.imagenProductoRepository = imagenProductoRepository;
    }

    public List<ImagenProducto> findAll() {
        return imagenProductoRepository.findAll();
    }

    public Optional<ImagenProducto> findById(Long id) {
        return imagenProductoRepository.findById(id);
    }

    public ImagenProducto save(ImagenProducto imagenProducto) {
        return imagenProductoRepository.save(imagenProducto);
    }

    public void deleteById(Long id) {
        imagenProductoRepository.deleteById(id);
    }
}
