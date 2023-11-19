package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Oficio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OficioRepositorio extends JpaRepository <Oficio, String>{

    @Query("SELECT o FROM Oficio o WHERE o.estado IS TRUE")
    public List<Oficio> listarOficiosActivos();

}