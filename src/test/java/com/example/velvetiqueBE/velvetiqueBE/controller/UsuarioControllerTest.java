package com.example.velvetiqueBE.velvetiqueBE.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.UsuarioDto;
import com.example.velvetiqueBE.velvetiqueBE.Dtos.UsuarioRequestDto;
import com.example.velvetiqueBE.velvetiqueBE.model.Rol;
import com.example.velvetiqueBE.velvetiqueBE.model.Usuario;
import com.example.velvetiqueBE.velvetiqueBE.service.RolService;
import com.example.velvetiqueBE.velvetiqueBE.service.UsuarioService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

class UsuarioControllerTest {

    private UsuarioService usuarioService;
    private RolService rolService;
    private UsuarioController controller;
    private MockMvc mockMvc;
    private Rol rol;

    @BeforeEach
    void setUp() {
        usuarioService = mock(UsuarioService.class);
        rolService = mock(RolService.class);
        controller = new UsuarioController(usuarioService, rolService);
        rol = new Rol();
        rol.setIdRol(7L);
        rol.setNombreRol("CLIENTE");
        when(rolService.findById(7L)).thenReturn(Optional.of(rol));
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createReturnsCreatedUserWithoutExposingPasswordHash() {
        LocalDateTime fechaRegistro = LocalDateTime.now();
        when(usuarioService.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            usuario.setIdUsuario(12L);
            usuario.setFechaRegistro(fechaRegistro);
            return usuario;
        });

        ResponseEntity<UsuarioDto> response = controller.create(request());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(12L, response.getBody().idUsuario());
        assertEquals(7L, response.getBody().idRol());
        assertEquals(fechaRegistro, response.getBody().fechaRegistro());
        verify(usuarioService).save(any(Usuario.class));
    }

    @Test
    void updatePreservesRegistrationDateAndUpdatesExistingUser() {
        LocalDateTime fechaRegistro = LocalDateTime.of(2024, 1, 2, 3, 4);
        Usuario existente = new Usuario();
        existente.setIdUsuario(12L);
        existente.setFechaRegistro(fechaRegistro);
        existente.setRol(rol);
        when(usuarioService.findById(12L)).thenReturn(Optional.of(existente));
        when(usuarioService.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioDto response = controller.update(12L, request());

        assertEquals(12L, response.idUsuario());
        assertEquals(fechaRegistro, response.fechaRegistro());
        assertEquals("Ana", response.nombre());
        assertEquals("ana@example.com", response.correo());
        assertEquals(rol, existente.getRol());
        verify(usuarioService).save(existente);
    }

    @Test
    void findByIdReturnsNotFoundWhenUserDoesNotExist() {
        when(usuarioService.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.findById(99L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void getEndpointSerializesDtoWithoutPasswordHash() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(12L);
        usuario.setNombre("Ana");
        usuario.setCorreo("ana@example.com");
        usuario.setEstado(true);
        usuario.setFechaRegistro(LocalDateTime.of(2024, 1, 2, 3, 4));
        usuario.setRol(rol);
        when(usuarioService.findById(12L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(12))
                .andExpect(jsonPath("$.idRol").value(7))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    void createEndpointRejectsInvalidEmail() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Ana",
                                  "correo": "no-es-correo",
                                  "passwordHash": "encoded-password",
                                  "estado": true,
                                  "idRol": 7
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEndpointReturnsCreatedDto() throws Exception {
        when(usuarioService.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            usuario.setIdUsuario(12L);
            usuario.setFechaRegistro(LocalDateTime.of(2024, 1, 2, 3, 4));
            return usuario;
        });

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Ana",
                                  "apellido": "Perez",
                                  "correo": "ana@example.com",
                                  "passwordHash": "encoded-password",
                                  "estado": true,
                                  "idRol": 7
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(12))
                .andExpect(jsonPath("$.idRol").value(7))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    private UsuarioRequestDto request() {
        return new UsuarioRequestDto(
                "12345678-9",
                "Ana",
                "Perez",
                "ana@example.com",
                "encoded-password",
                null,
                true,
                7L);
    }
}
