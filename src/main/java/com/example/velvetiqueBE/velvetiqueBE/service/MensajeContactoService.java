package com.example.velvetiqueBE.velvetiqueBE.service;

import com.example.velvetiqueBE.velvetiqueBE.model.MensajeContacto;
import com.example.velvetiqueBE.velvetiqueBE.repository.MensajeContactoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MensajeContactoService {

    private final MensajeContactoRepository mensajeContactoRepository;

    public MensajeContactoService(MensajeContactoRepository mensajeContactoRepository) {
        this.mensajeContactoRepository = mensajeContactoRepository;
    }

    public List<MensajeContacto> findAll() {
        return mensajeContactoRepository.findAll();
    }

    public Optional<MensajeContacto> findById(Long id) {
        return mensajeContactoRepository.findById(id);
    }

    public MensajeContacto save(MensajeContacto mensajeContacto) {
        return mensajeContactoRepository.save(mensajeContacto);
    }

    public void deleteById(Long id) {
        mensajeContactoRepository.deleteById(id);
    }
}
