package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Oficio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Maresca
 */
public interface OficioRepositorio extends JpaRepository <Oficio, String>{
    
}
