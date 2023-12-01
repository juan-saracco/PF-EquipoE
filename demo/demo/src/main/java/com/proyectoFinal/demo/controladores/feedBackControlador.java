
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.FeedBackServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/feedBackUsuario")
public class feedBackControlador {
    
    @Autowired
    private FeedBackServicio feedBackServicio;
    
    @GetMapping
    public String feedBackUsuario(){
        return "feedBackUsuario";
    }
    
    @PostMapping("/crearFeedBack")
    public String crearFeedBack(ModelMap ModeloFeedBack, @RequestParam int calificacion, @RequestParam String feedback){
        try{
            feedBackServicio.crearFeedBack(calificacion, feedback);
            ModeloFeedBack.addAttribute("Exito", "FeedBack creado correctamente.");
            return "inicio.html";
        }catch (MiException e){
            List<FeedBack> feedBacks = feedBackServicio.listarFeedBacks();
            ModeloFeedBack.addAttribute("feedBacks", feedBacks);
            ModeloFeedBack.put("Error", e.getMessage());
            
        }
        return "feedBackUsuario";
    }
    
}