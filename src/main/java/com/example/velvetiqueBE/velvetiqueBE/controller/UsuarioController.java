package com.example.velvetiqueBE.velvetiqueBE.controller;

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

import com.example.velvetiqueBE.velvetiqueBE.Dtos.UsuarioDto;
import com.example.velvetiqueBE.velvetiqueBE.Dtos.UsuarioRequestDto;
import com.example.velvetiqueBE.velvetiqueBE.model.Usuario;
import com.example.velvetiqueBE.velvetiqueBE.service.RolService;
import com.example.velvetiqueBE.velvetiqueBE.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping
    public List<UsuarioDto> findAll() {
        return usuarioService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public UsuarioDto findById(@PathVariable Long id) {
        return toDto(usuarioService.findById(id)
                .orElseThrow(() -> notFound("Usuario", id)));
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> create(@Valid @RequestBody UsuarioRequestDto dto) {
        Usuario usuario = toEntity(dto);
        usuario.setIdUsuario(null);
        usuario.setFechaRegistro(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(usuarioService.save(usuario)));
    }

    @PutMapping("/{id}")
    public UsuarioDto update(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto dto) {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> notFound("Usuario", id));
        applyRequest(usuario, dto);
        return toDto(usuarioService.save(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioService.findById(id).isEmpty()) {
            throw notFound("Usuario", id);
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioDto toDto(Usuario usuario) {
        return new UsuarioDto(usuario.getIdUsuario(), usuario.getRun(), usuario.getNombre(),
                usuario.getApellido(), usuario.getCorreo(), usuario.getFechaNacimiento(),
                usuario.getEstado(), usuario.getFechaRegistro(), usuario.getRol().getIdRol());
    }

    private Usuario toEntity(UsuarioRequestDto dto) {
        Usuario usuario = new Usuario();
        applyRequest(usuario, dto);
        return usuario;
    }

    private void applyRequest(Usuario usuario, UsuarioRequestDto dto) {
        usuario.setRun(dto.run());
        usuario.setNombre(dto.nombre());
        usuario.setApellido(dto.apellido());
        usuario.setCorreo(dto.correo());
        usuario.setPasswordHash(dto.passwordHash());
        usuario.setFechaNacimiento(dto.fechaNacimiento());
        usuario.setEstado(dto.estado());
        usuario.setRol(rolService.findById(dto.idRol())
                .orElseThrow(() -> notFound("Rol", dto.idRol())));
    }

    private ResponseStatusException notFound(String entity, Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, entity + " no encontrado: " + id);
    }
}
