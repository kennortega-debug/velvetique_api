package com.example.velvetiqueBE.velvetiqueBE.repository;

import com.example.velvetiqueBE.velvetiqueBE.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
