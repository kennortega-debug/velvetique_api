package com.example.velvetiqueBE.velvetiqueBE.service;

import com.example.velvetiqueBE.velvetiqueBE.model.Direccion;
import com.example.velvetiqueBE.velvetiqueBE.repository.DireccionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DireccionService {

    private final DireccionRepository direccionRepository;

    public DireccionService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    public List<Direccion> findAll() {
        return direccionRepository.findAll();
    }

    public Optional<Direccion> findById(Long id) {
        return direccionRepository.findById(id);
    }

    public Direccion save(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    public void deleteById(Long id) {
        direccionRepository.deleteById(id);
    }
}
