
package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository <Pedido, String > {
    
}
