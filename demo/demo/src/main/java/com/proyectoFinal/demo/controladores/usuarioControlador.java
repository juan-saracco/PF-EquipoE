package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Imagen;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class usuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    //@GetMapping("/obtener")
    //public String obtenerUsuarios(){
    //    return 
    //}

    @GetMapping("/registrar")
    public String registrar(){
        return "registroUsuario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String telefono, @RequestParam String direccion, @RequestParam MultipartFile archivo, @RequestParam String password, @RequestParam String password2) throws MiException {
        usuarioServicio.registrar(archivo, nombre, apellido, DNI, email , direccion, telefono, password, password2 );
        return "registroUsuario.html";
    }



}
