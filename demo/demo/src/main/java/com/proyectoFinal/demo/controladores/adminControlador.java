package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class adminControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "panelAdmin.html";
    }

    /*
    @GetMapping("/feedback")
    public String administrarFeedback(Model model) {

        return "adminFeedback.html";
    }
    */

    // Mapeo para la creación de nuevos servicios
    @GetMapping("/servicio/nuevo")
    public String crearServicio(ModelMap model) {
        // Lógica para preparar el formulario de creación de servicios
        return "crearServicio.html";
    }

    // Lógica para procesar la creación de nuevos servicios
    @PostMapping("/services/new")
    public String createService() {
        // Lógica para procesar la creación del servicio
        return "redirect:/admin/dashboard";
    }


}
