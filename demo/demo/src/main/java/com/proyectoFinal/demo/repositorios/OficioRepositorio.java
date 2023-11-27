package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Oficio;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OficioRepositorio extends JpaRepository <Oficio, String>{

    @Query("SELECT o FROM Oficio o WHERE o.estado IS TRUE")
    public List<Oficio> listarOficiosActivos();

    @Query("SELECT o FROM Oficio o WHERE o.denominacion = :aux")
    public Oficio buscarOficioPorDenom(@Param("aux")String denominacion);

    public Optional<Oficio> findById(String id);

    public Oficio getReferenceById(String id);

}