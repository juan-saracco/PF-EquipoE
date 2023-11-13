package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
}
