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

import com.example.velvetiqueBE.velvetiqueBE.Dtos.CarritoDto;
import com.example.velvetiqueBE.velvetiqueBE.model.Carrito;
import com.example.velvetiqueBE.velvetiqueBE.service.CarritoService;
import com.example.velvetiqueBE.velvetiqueBE.service.DireccionService;
import com.example.velvetiqueBE.velvetiqueBE.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    private final CarritoService carritoService;
    private final UsuarioService usuarioService;
    private final DireccionService direccionService;

    public CarritoController(CarritoService carritoService, UsuarioService usuarioService,
            DireccionService direccionService) {
        this.carritoService = carritoService;
        this.usuarioService = usuarioService;
        this.direccionService = direccionService;
    }

    @GetMapping
    public List<CarritoDto> findAll() {
        return carritoService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public CarritoDto findById(@PathVariable Long id) {
        return toDto(carritoService.findById(id)
                .orElseThrow(() -> notFound("Carrito", id)));
    }

    @PostMapping
    public ResponseEntity<CarritoDto> create(@Valid @RequestBody CarritoDto dto) {
        Carrito carrito = toEntity(dto);
        carrito.setIdCarrito(null);
        carrito.setFechaCreacion(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(carritoService.save(carrito)));
    }

    @PutMapping("/{id}")
    public CarritoDto update(@PathVariable Long id, @Valid @RequestBody CarritoDto dto) {
        if (carritoService.findById(id).isEmpty()) {
            throw notFound("Carrito", id);
        }
        Carrito carrito = toEntity(dto);
        carrito.setIdCarrito(id);
        carrito.setFechaCreacion(null);
        return toDto(carritoService.save(carrito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (carritoService.findById(id).isEmpty()) {
            throw notFound("Carrito", id);
        }
        carritoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CarritoDto toDto(Carrito carrito) {
        return new CarritoDto(carrito.getIdCarrito(), carrito.getUsuario().getIdUsuario(),
                carrito.getDireccion() == null ? null : carrito.getDireccion().getIdDireccion(),
                carrito.getFechaCreacion(), carrito.getFechaCompra(), carrito.getTotal(),
                carrito.getEstado(), carrito.getMetodoPago(), carrito.getEstadoPago());
    }

    private Carrito toEntity(CarritoDto dto) {
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuarioService.findById(dto.idUsuario())
                .orElseThrow(() -> notFound("Usuario", dto.idUsuario())));
        if (dto.idDireccion() != null) {
            carrito.setDireccion(direccionService.findById(dto.idDireccion())
                    .orElseThrow(() -> notFound("Direccion", dto.idDireccion())));
        }
        carrito.setFechaCompra(dto.fechaCompra());
        carrito.setTotal(dto.total());
        carrito.setEstado(dto.estado());
        carrito.setMetodoPago(dto.metodoPago());
        carrito.setEstadoPago(dto.estadoPago());
        return carrito;
    }

    private ResponseStatusException notFound(String entity, Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, entity + " no encontrado: " + id);
    }
}
