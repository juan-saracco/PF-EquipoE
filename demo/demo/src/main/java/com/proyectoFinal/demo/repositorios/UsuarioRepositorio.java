package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u from Usuario u WHERE u.email = :aux")
    public Usuario buscarPorEmail(@Param("aux")String email);

    @Query("SELECT u from Usuario u WHERE u.id = :aux")
    public Usuario buscarPorId(@Param("aux")String id);

    @Query("SELECT u from Usuario u WHERE u.DNI = :aux")
    public Usuario buscarPorDNI(@Param("aux")String DNI);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'USER'")
    public List<Usuario> listarTodosUsuarios();


}
