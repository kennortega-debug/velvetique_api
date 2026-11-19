package com.example.velvetiqueBE.velvetiqueBE.service;

import com.example.velvetiqueBE.velvetiqueBE.model.DetalleCarrito;
import com.example.velvetiqueBE.velvetiqueBE.repository.DetalleCarritoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DetalleCarritoService {

    private final DetalleCarritoRepository detalleCarritoRepository;

    public DetalleCarritoService(DetalleCarritoRepository detalleCarritoRepository) {
        this.detalleCarritoRepository = detalleCarritoRepository;
    }

    public List<DetalleCarrito> findAll() {
        return detalleCarritoRepository.findAll();
    }

    public Optional<DetalleCarrito> findById(Long id) {
        return detalleCarritoRepository.findById(id);
    }

    public DetalleCarrito save(DetalleCarrito detalleCarrito) {
        return detalleCarritoRepository.save(detalleCarrito);
    }

    public void deleteById(Long id) {
        detalleCarritoRepository.deleteById(id);
    }
}
