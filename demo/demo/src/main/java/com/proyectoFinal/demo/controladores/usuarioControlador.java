package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class usuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Sujeto a cambio //
    @GetMapping("/obtener")
    public String obtenerUsuarios(ModelMap model){

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        model.addAttribute("usuarios", usuarios);
        return "usuarios.html";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "registroUsuario.html";
    }


    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            usuarioServicio.registrar(nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo);

            modelo.put("Exito", "Usuario registrado correctamente!");
            return "index.html";

        } catch (MiException e) {
            modelo.put("Error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido",apellido);

          return "registroUsuario.html";
        }
    }


    @PostMapping("/modificar/{id}")
    public String modificar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, ModelMap modelo){
        try {
            usuarioServicio.actualizar(id, nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo);
            modelo.put("exito", "se modifico el usuario correctamente");
        } catch (MiException e) {
            modelo.put("error", "Error: No se pudo modifica el usuario.");
        }

        return "modificarUsuario.html";
    }
    @PostMapping("/cambiarestado/{id}")
    public String cambiarestadoPorId(@PathVariable String id, ModelMap modelo){
        try {
            modelo.addAttribute("exito", "Se dio de baja el usuario correctamente");
            usuarioServicio.cambiarestado(id);
        }catch (Exception e){
            modelo.put("error", "Error: no se pudo borrar el usuario");
        }

        return "modificarUsuario.html";
    }
}
