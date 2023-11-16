package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Oficios;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String registro(MultipartFile archivo, String nombre, String apellido, String DNI, String email, String direccion, String telefono, String password, String password2, Oficios oficio, String descripcion, Integer tarifaPorHora, Double calificacion, ModelMap modelo){

        try {
            proveedorservicio.registrar(MultipartFile archivo, String nombre, String apellido, Enum oficio, String descripcion, Integer tarifaPorHora, String telefono, Double calificacion);
            modelo.put("exito", "Usuario registrado correctamente.");
            return "index.html";

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            return "registro.html";
        }

    }
    }
