package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor,String> {

     @Query("SELECT u from Proveedor u WHERE u.email = :aux")
    public Proveedor buscarPorEmail(@Param("aux")String email);
    
    @Query("SELECT u FROM Proveedor u WHERE u.estado IS TRUE")
    public List<Proveedor> listarProveedoresActivos();
}
