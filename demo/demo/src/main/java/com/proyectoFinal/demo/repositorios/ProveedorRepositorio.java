package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Usuario,String> {

}
