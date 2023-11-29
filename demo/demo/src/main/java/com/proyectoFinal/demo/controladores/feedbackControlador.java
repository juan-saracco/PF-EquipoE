/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoFinal.demo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author LENOVO
 */
 
@Controller
@RequestMapping("/feedbackUsuario")
public class feedbackControlador {
    
    @GetMapping
    public String mostrarFormulario(Model model){
        return "feedbackUsuario";
    }
    
    @PostMapping("/enviar-feedback")
    public String enviarFeedback(Model model, int calificacion, String feedback){
        
        return "";
    }
    
}