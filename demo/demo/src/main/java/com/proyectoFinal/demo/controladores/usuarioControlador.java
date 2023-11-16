package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Imagen;
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

        List<Usuario> usuarios = usuarioServicio.leerUsuarios();

        model.addAttribute("usuarios", usuarios);
        return "usuarios.html";
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "registroUsuario.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(MultipartFile archivo,@PathVariable String id ,@RequestParam String nombre, @RequestParam String apellido, @RequestParam String documento, @RequestParam String telefono, @RequestParam String direccion, @RequestParam String mail, @RequestParam String contrasenia,  @RequestParam String contrasenia2, ModelMap model){
        try {
            usuarioServicio.actualizar(archivo, id, nombre, apellido, documento, mail, direccion, telefono, contrasenia, contrasenia2);
            model.put("exito", "se modifico el usuario correctamente");
        } catch (MiException e) {
            model.put("error", "Error: No se pudo modifica el usuario.");
        }

        return "modificarUsuario.html";
    }

    @PostMapping("/registro")
    public String registro(MultipartFile Archivo ,@RequestParam String Nombre, @RequestParam String Apellido, @RequestParam String Documento, @RequestParam String Telefono, @RequestParam String Direccion, @RequestParam String Mail, @RequestParam String Contrasenia, @RequestParam String Confirmar, ModelMap modelo) {

        try {
            usuarioServicio.registrar(Archivo, Nombre, Apellido, Documento, Direccion, Telefono, Mail, Contrasenia, Confirmar);

            modelo.put("exito", "Usuario registrado correctamente!");
            return "index.html";

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", Nombre);
            modelo.put("email", Mail);
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/borrar/{id}")
    public String borrarPorId(@PathVariable String id, ModelMap modelo){
        try {
            modelo.addAttribute("exito", "Se elimino el usuario correctamente");
            usuarioServicio.eliminarUsuario(id);
        }catch (Exception e){
            modelo.put("error", "Error: no se pudo borrar el usuario");
        }

        return "modificarUsuario.html";
    }
}
