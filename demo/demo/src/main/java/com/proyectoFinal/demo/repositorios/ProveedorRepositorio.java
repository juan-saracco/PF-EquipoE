package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Proveedor;
import java.util.List;

import com.proyectoFinal.demo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor,String> {

    @Query("SELECT p from Proveedor p WHERE p.email = :aux")
    public Proveedor buscarPorEmail(@Param("aux")String email);

    @Query("SELECT p from Proveedor p WHERE p.id = :aux")
    public Proveedor buscarPorId(@Param("aux")String id);
    
    @Query("SELECT p FROM Proveedor p WHERE p.estado IS TRUE")
    public List<Proveedor> listarProveedoresActivos();
    
    @Query("SELECT p FROM Proveedor p WHERE p.estado IS TRUE AND p.oficio.denominacion = :aux ORDER BY p.apellido ASC")
    public List<Proveedor> listarProvPorParamApellido(@Param("aux")String denominacion);
    
    @Query("SELECT p FROM Proveedor p WHERE p.estado IS TRUE AND p.oficio.denominacion = :aux ORDER BY p.tarifaPorHora ASC")
    public List<Proveedor> listarProvPorParamTarifa(@Param("aux")String denominacion);
    
    @Query("SELECT p FROM Proveedor p WHERE p.estado IS TRUE AND p.nombre LIKE :aux OR p.apellido LIKE :aux ORDER BY p.apellido ASC")
    public List<Proveedor> listarProvPorParamNombre(@Param("aux")String nombreAux);
}
