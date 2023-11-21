package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/proveedor")
public class proveedorControlador {
    @Autowired
   public ProveedorServicio proveedorservicio;

    @GetMapping("/registar")
    public String registar(){
        return "registroProveedor.html";
    }

    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, @RequestParam Oficio oficio, @RequestParam String descripcion, @RequestParam Double tarifaPorHora, ModelMap modelo){

        try {
            proveedorservicio.registrar(nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo, oficio, descripcion, tarifaPorHora);
            modelo.put("Exito", "Usuario registrado correctamente.");
            return "index.html";

        } catch (MiException e) {
            modelo.put("Error", e.getMessage());
            return "registroProveedor.html";
        }
    }

    @PostMapping("/cambiarestado/{id}")
    public String cambiarestadoPorId(@PathVariable String id, ModelMap modelo){
        try {
            modelo.addAttribute("Exito", "Se dio de baja el usuario correctamente");
            proveedorservicio.cambiarestado(id);
        }catch (Exception e){
            modelo.put("Error", "Error: no se pudo borrar el usuario");
        }

        return "modificarUsuario.html";
    }
    }
