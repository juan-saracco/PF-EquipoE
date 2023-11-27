package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.OficioServicio;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/proveedor")
public class proveedorControlador {
    @Autowired
    public ProveedorServicio proveedorservicio;
    @Autowired
    public OficioServicio oficioservicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "registroProveedor.html";
    }

    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, @RequestParam Oficio oficio, @RequestParam String descripcion, @RequestParam Double tarifaPorHora, ModelMap ModeloProveedor) {
        List<Oficio> SelectOficio = oficioservicio.listarOficios();
        ModeloProveedor.addAttribute("Alloficios", SelectOficio);
        try {
            proveedorservicio.registrar(nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo, oficio, descripcion, tarifaPorHora);
            ModeloProveedor.put("Exito", "Usuario registrado correctamente.");
            return "redirect:/";

        } catch (MiException e) {
            ModeloProveedor.put("Error", e.getMessage());
            ModeloProveedor.put("nombre", nombre);
            ModeloProveedor.put("apellido", apellido);
            ModeloProveedor.put("email", email);
            ModeloProveedor.put("DNI", DNI);
            ModeloProveedor.put("telefono", telefono);
            ModeloProveedor.put("direccion", direccion);
            ModeloProveedor.put("archivo", archivo);
            ModeloProveedor.put("oficio", oficio);
            ModeloProveedor.put("descripcion", descripcion);
            ModeloProveedor.put("tarifaPorHora", tarifaPorHora);

            return "registroProveedor.html";
        }
    }

    @PostMapping("/cambiarestado/{id}")
    public String cambiarestadoPorId(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.addAttribute("Exito", "Se dio de baja el usuario correctamente");
            proveedorservicio.cambiarestado(id);
        } catch (Exception e) {
            modelo.put("Error", "Error: no se pudo borrar el usuario");
        }

        return "modificarUsuario.html";
    }
}
