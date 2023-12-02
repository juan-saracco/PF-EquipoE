package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import com.proyectoFinal.demo.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class usuarioControlador {
    @Autowired
    public ProveedorServicio proveedorservicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    //conectada a panel admin//
    @GetMapping("/obtener")
    public String obtenerPanelAdminUsuarios(ModelMap model) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        model.addAttribute("usuarios", usuarios);
        return "adminUsuarios.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registroUsuario.html";
    }

    //Funcionando
    @PostMapping("/registro")
    public String registro(@RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            usuarioServicio.registrarUsuario(nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo);

            modelo.addAttribute("Exito", "Usuario registrado correctamente. Ingrese nuevamente su usuario");
            return "login.html";

        } catch (MiException e) {
            modelo.put("Error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("DNI", DNI);
            modelo.put("telefono", telefono);
            modelo.put("direccion", direccion);
            modelo.put("archivo", archivo);

            return "registroUsuario.html";
        }
    }

/*    Eliminado por el cambio de registrorapido en el index
   @PostMapping("/registrorapido")
   public String registrorapido(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String password, ModelMap ModeloUsuario) {
       try {

           usuarioServicio.registrorapido(nombre, apellido, email, password);
           ModeloUsuario.addAttribute("Exito", "Usuario registrado correctamente. Ingrese nuevamente su usuario");
           return "login.html";

       } catch (MiException e) {
           ModeloUsuario.put("Error", e.getMessage());
           ModeloUsuario.put("nombre", nombre);
           ModeloUsuario.put("apellido", apellido);
           ModeloUsuario.put("email", email);
           ModeloUsuario.put("password", password);

           return "index.html";
       }
   }

    @PostMapping("/modificadorapido")
    public String modificadorapido(MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioServicio.actualizar(id, nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo);
            modelo.put("Exito", "Se modifico el usuario correctamente");
        } catch (MiException e) {
            modelo.put("Error", "Error: No se pudo modificar el usuario.");
        }

        return "index.html";
    }*/

    /*@GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo2) {
        modelo2.put("usuarioID", usuarioServicio.getOne(id));
        return "modificarUsuario.html";
    }*/
/*
    @PostMapping("/modificando/{id}")
    public String modificando(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, ModelMap modelo){
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
            modelo.addAttribute("Exito", "Se dio de baja el usuario correctamente");
            usuarioServicio.cambiarestado(id);
        }catch (Exception e){
            modelo.put("Error", "Error: no se pudo borrar el usuario");
        }

        return "modificarUsuario.html";
    }*/


/*    @GetMapping("/perfil")
    public String perfilid(ModelMap model) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        model.addAttribute("usuariosID", usuarios);
        return "modificarUsuario.html";
    }*/

    //FUNCIONA PERO NO ACTUALIZA
   /* @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/perfil")
    public String mostrarperfil(ModelMap modelo2, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo2.addAttribute("usuario", usuario);
        return "modificarUsuario.html";
    }*/
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String mostrarperfil(ModelMap modelo2, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo2.addAttribute("usuario", usuario);
        return "modificarUsuario.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String modificando(@PathVariable String id, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, ModelMap modelo, RedirectAttributes redirectAttributes, HttpSession session) {


        try {
            // Actualizar el usuario en la base de datos
            Usuario usuarioActualizado = usuarioServicio.actualizar(id, nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo);

            // Almacenar el usuario actualizado en la sesión
            session.setAttribute("usuariosession", usuarioActualizado);

            // Agregar atributos para mostrar en la vista
            modelo.addAttribute("usuario", usuarioActualizado);
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("apellido", apellido);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("DNI", DNI);
            redirectAttributes.addFlashAttribute("telefono", telefono);
            redirectAttributes.addFlashAttribute("direccion", direccion);
            redirectAttributes.addFlashAttribute("archivo", archivo);
            redirectAttributes.addFlashAttribute("Exito", "Se modifico el usuario correctamente");
            return "redirect:../perfil";
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("Error", "Error: No se pudo modificar el usuario.");
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("apellido", apellido);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("DNI", DNI);
            redirectAttributes.addFlashAttribute("telefono", telefono);
            redirectAttributes.addFlashAttribute("direccion", direccion);
            redirectAttributes.addFlashAttribute("archivo", archivo);
            return "redirect:../perfil";
        }
    }


}

