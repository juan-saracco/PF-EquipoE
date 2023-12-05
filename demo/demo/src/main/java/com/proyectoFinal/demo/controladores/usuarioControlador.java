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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class usuarioControlador {
    @Autowired
    public ProveedorServicio proveedorservicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    //Conectada a panel admin//
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
    public String registro(@RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, ModelMap modelo, RedirectAttributes redi) {

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

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String mostrarperfil(ModelMap modelo2, HttpSession session) throws MiException {
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

    //Cambiar entre un usuario activo o inactivo
    @GetMapping("/desactReactUsuarios/{id}")
    public String estadoUsuario(@PathVariable String id) throws MiException {
        usuarioServicio.cambiarestado(id);

        return "redirect:/usuario/listaTodos";
    }

    //MUESTRA TODOS LOS USUARIOS, TANTO ACTIVOS COMO DADOS DE BAJA ->SIRVE PARA MOSTRAR LOS OFICIOS AL ADMINISTRADOR
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listaTodos")
    public String listarTodos(ModelMap modelo, @ModelAttribute("exi") String ex) {

        List<Usuario> usuarios = usuarioServicio.listarTodosUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listarUsuarios.html";
    }

}

