package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumidorRepositorio extends JpaRepository<Consumidor, String> {
     @Query("SELECT u from Consumidor u WHERE u.email = :aux")
    public Consumidor buscarPorEmail(@Param("aux")String email);
}
