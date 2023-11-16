package com.proyectoFinal.demo.repositorios;

import com.proyectoFinal.demo.entidades.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepositorio extends JpaRepository <FeedBack, String>{  
}
