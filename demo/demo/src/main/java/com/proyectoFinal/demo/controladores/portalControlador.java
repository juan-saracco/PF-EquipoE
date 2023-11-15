package com.proyectoFinal.demo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class portalControlador {

    @GetMapping
    public String inicio(){
        return "index.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
}
