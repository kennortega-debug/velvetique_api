package com.example.velvetiqueBE.velvetiqueBE.controller;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.MensajeContactoDto;
import com.example.velvetiqueBE.velvetiqueBE.model.MensajeContacto;
import com.example.velvetiqueBE.velvetiqueBE.service.MensajeContactoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/mensajes-contacto")
public class MensajeContactoController {

    private final MensajeContactoService mensajeContactoService;

    public MensajeContactoController(MensajeContactoService mensajeContactoService) {
        this.mensajeContactoService = mensajeContactoService;
    }

    @GetMapping
    public List<MensajeContactoDto> findAll() {
        return mensajeContactoService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public MensajeContactoDto findById(@PathVariable Long id) {
        return toDto(mensajeContactoService.findById(id)
                .orElseThrow(() -> notFound(id)));
    }

    @PostMapping
    public ResponseEntity<MensajeContactoDto> create(@Valid @RequestBody MensajeContactoDto dto) {
        MensajeContacto mensaje = toEntity(dto);
        mensaje.setIdMensaje(null);
        mensaje.setFechaEnvio(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(mensajeContactoService.save(mensaje)));
    }

    @PutMapping("/{id}")
    public MensajeContactoDto update(@PathVariable Long id, @Valid @RequestBody MensajeContactoDto dto) {
        if (mensajeContactoService.findById(id).isEmpty()) {
            throw notFound(id);
        }
        MensajeContacto mensaje = toEntity(dto);
        mensaje.setIdMensaje(id);
        mensaje.setFechaEnvio(null);
        return toDto(mensajeContactoService.save(mensaje));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (mensajeContactoService.findById(id).isEmpty()) {
            throw notFound(id);
        }
        mensajeContactoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MensajeContactoDto toDto(MensajeContacto mensaje) {
        return new MensajeContactoDto(mensaje.getIdMensaje(), mensaje.getNombre(), mensaje.getCorreo(),
                mensaje.getAsunto(), mensaje.getMensaje(), mensaje.getFechaEnvio(), mensaje.getEstado());
    }

    private MensajeContacto toEntity(MensajeContactoDto dto) {
        MensajeContacto mensaje = new MensajeContacto();
        mensaje.setNombre(dto.nombre());
        mensaje.setCorreo(dto.correo());
        mensaje.setAsunto(dto.asunto());
        mensaje.setMensaje(dto.mensaje());
        mensaje.setEstado(dto.estado());
        return mensaje;
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Mensaje de contacto no encontrado: " + id);
    }
}
