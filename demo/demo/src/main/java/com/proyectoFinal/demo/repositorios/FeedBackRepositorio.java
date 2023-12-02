package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.FeedBack;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepositorio extends JpaRepository <FeedBack, String>{

    @Query("SELECT f FROM FeedBack f WHERE f.estado IS TRUE")
    public List<FeedBack> listarFeedBacksActivos();
}