package com.proyectoFinal.demo.repositorios;



import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepositorio extends JpaRepository <Pedido, String > {

    @Query("SELECT p from Pedido p WHERE p.solicitud = :aux")
    public Pedido buscarPorSolicitud(@Param("aux")String solicitud);

    @Query("SELECT p from Pedido p WHERE p.id = :aux")
    public Pedido buscarPorId(@Param("aux")String id);

    @Query("SELECT p from Pedido p WHERE p.proveedor.nombre = :aux")
    public Pedido buscarPorProveedor(@Param("aux") String nombre);

    @Query("SELECT p from Pedido p WHERE p.usuario.nombre = :aux")
    public Pedido buscarPorUsuario(@Param("aux")String nombre);

    @Query("SELECT p from Pedido p WHERE p.usuario.id = :aux")
    public List <Pedido> buscarPorIdUsuario(@Param("aux")String id);

    @Query("SELECT p from Pedido p WHERE p.proveedor.id = :aux")
    public List <Pedido>  buscarPorIdProveedor(@Param("aux") String id);
}