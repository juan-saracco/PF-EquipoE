package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.FeedBack;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepositorio extends JpaRepository <FeedBack, String>{

    @Query("SELECT f FROM FeedBack f WHERE f.estado IS TRUE")
    public List<FeedBack> listarFeedbacksActivos();

    @Query("SELECT f FROM FeedBack f")
    public List<FeedBack> listarFeedbacks();

    @Query("SELECT f FROM FeedBack f WHERE f.pedido.id = :aux")
    public FeedBack buscarFeedbackPorId(@Param("aux")String id);
}