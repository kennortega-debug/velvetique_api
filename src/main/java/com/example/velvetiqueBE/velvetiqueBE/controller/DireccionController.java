package com.example.velvetiqueBE.velvetiqueBE.controller;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.DireccionDto;
import com.example.velvetiqueBE.velvetiqueBE.model.Direccion;
import com.example.velvetiqueBE.velvetiqueBE.service.DireccionService;
import com.example.velvetiqueBE.velvetiqueBE.service.UsuarioService;
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
@RequestMapping("/api/direcciones")
public class DireccionController {

    private final DireccionService direccionService;
    private final UsuarioService usuarioService;

    public DireccionController(DireccionService direccionService, UsuarioService usuarioService) {
        this.direccionService = direccionService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<DireccionDto> findAll() {
        return direccionService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public DireccionDto findById(@PathVariable Long id) {
        return toDto(direccionService.findById(id)
                .orElseThrow(() -> notFound("Direccion", id)));
    }

    @PostMapping
    public ResponseEntity<DireccionDto> create(@Valid @RequestBody DireccionDto dto) {
        Direccion direccion = toEntity(dto);
        direccion.setIdDireccion(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(direccionService.save(direccion)));
    }

    @PutMapping("/{id}")
    public DireccionDto update(@PathVariable Long id, @Valid @RequestBody DireccionDto dto) {
        if (direccionService.findById(id).isEmpty()) {
            throw notFound("Direccion", id);
        }
        Direccion direccion = toEntity(dto);
        direccion.setIdDireccion(id);
        return toDto(direccionService.save(direccion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (direccionService.findById(id).isEmpty()) {
            throw notFound("Direccion", id);
        }
        direccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private DireccionDto toDto(Direccion direccion) {
        return new DireccionDto(direccion.getIdDireccion(), direccion.getUsuario().getIdUsuario(),
                direccion.getRegion(), direccion.getComuna(), direccion.getCalle(),
                direccion.getNumero(), direccion.getComplemento(), direccion.getEsPrincipal());
    }

    private Direccion toEntity(DireccionDto dto) {
        Direccion direccion = new Direccion();
        direccion.setUsuario(usuarioService.findById(dto.idUsuario())
                .orElseThrow(() -> notFound("Usuario", dto.idUsuario())));
        direccion.setRegion(dto.region());
        direccion.setComuna(dto.comuna());
        direccion.setCalle(dto.calle());
        direccion.setNumero(dto.numero());
        direccion.setComplemento(dto.complemento());
        direccion.setEsPrincipal(dto.esPrincipal());
        return direccion;
    }

    private ResponseStatusException notFound(String entity, Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, entity + " no encontrado: " + id);
    }
}
