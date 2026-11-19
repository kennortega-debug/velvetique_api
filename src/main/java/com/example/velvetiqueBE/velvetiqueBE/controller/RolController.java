package com.example.velvetiqueBE.velvetiqueBE.controller;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.RolDto;
import com.example.velvetiqueBE.velvetiqueBE.model.Rol;
import com.example.velvetiqueBE.velvetiqueBE.service.RolService;
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
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<RolDto> findAll() {
        return rolService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public RolDto findById(@PathVariable Long id) {
        return toDto(rolService.findById(id)
                .orElseThrow(() -> notFound(id)));
    }

    @PostMapping
    public ResponseEntity<RolDto> create(@Valid @RequestBody RolDto dto) {
        Rol rol = toEntity(dto);
        rol.setIdRol(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(rolService.save(rol)));
    }

    @PutMapping("/{id}")
    public RolDto update(@PathVariable Long id, @Valid @RequestBody RolDto dto) {
        if (rolService.findById(id).isEmpty()) {
            throw notFound(id);
        }
        Rol rol = toEntity(dto);
        rol.setIdRol(id);
        return toDto(rolService.save(rol));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (rolService.findById(id).isEmpty()) {
            throw notFound(id);
        }
        rolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RolDto toDto(Rol rol) {
        return new RolDto(rol.getIdRol(), rol.getNombreRol(), rol.getDescripcion());
    }

    private Rol toEntity(RolDto dto) {
        Rol rol = new Rol();
        rol.setNombreRol(dto.nombreRol());
        rol.setDescripcion(dto.descripcion());
        return rol;
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado: " + id);
    }
}
