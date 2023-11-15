package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.UsuarioServicio;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Password;

import javax.persistence.criteria.CriteriaBuilder;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping                  // en caso de ser necesario ("/inicio")
    public String inicio() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @PostMapping("/registro")
    public String registro(MultipartFile Archivo ,@RequestParam String Nombre, @RequestParam String Apellido, @RequestParam String Documento, @RequestParam String Telefono, @RequestParam String Direccion, @RequestParam String Mail, @RequestParam String Contraseña, @RequestParam String Confirmar, ModelMap modelo) {

        try {
            usuarioServicio.registrar(Archivo,Nombre,Apellido,Documento,Direccion,Telefono,Mail,Contraseña,Confirmar);  //Solucionar

            modelo.put("exito", "Usuario registrado correctamente!");
            return "index.html";

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", Nombre);
            modelo.put("email", Mail);
            throw new RuntimeException(e);
        }                                                  //Contro+Alt+T   en intellij para aplicar bucles o trycatch

    }

}






