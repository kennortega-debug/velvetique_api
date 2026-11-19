package com.example.velvetiqueBE.velvetiqueBE.repository;

import com.example.velvetiqueBE.velvetiqueBE.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
