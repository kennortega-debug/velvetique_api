package com.example.velvetiqueBE.velvetiqueBE.service;

import com.example.velvetiqueBE.velvetiqueBE.model.Carrito;
import com.example.velvetiqueBE.velvetiqueBE.repository.CarritoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> findById(Long id) {
        return carritoRepository.findById(id);
    }

    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void deleteById(Long id) {
        carritoRepository.deleteById(id);
    }
}
