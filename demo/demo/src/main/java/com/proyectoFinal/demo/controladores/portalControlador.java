package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.OficioServicio;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import com.proyectoFinal.demo.servicio.UsuarioServicio;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    public OficioServicio oficioServicio;

    @Autowired
    public ProveedorServicio proveedorServicio;

    /*Pagina de presentacion inicial accesible para todos(visitantes)*/

    @GetMapping("/inicio")
    public String inicio() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        if (error != null) {
            modelo.put("Error", "Usuario o contraseña incorrectos");

            return "login.html";
        }
        return "login.html";
    }

    /*Pagina inicial solo accesible para usuarios registrados*/

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/iniciado")
    public String inicioAdmin(HttpSession session) {

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuariosession");
        Proveedor proveedorLogueado = (Proveedor) session.getAttribute("proveedorsession");

        if (usuarioLogueado.getRol().toString().equals("USER") || usuarioLogueado.getRol().toString().equals("PROVEEDOR")) {
            return "redirect:/inicioLogueado";
        }

        if (usuarioLogueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/";
    }

    //Muestra vista una vez que el usuario esta logueado
    @GetMapping("/inicioLogueado")
    public String inicioLog() {
        return "inicioLogueado.html";
    }

    @GetMapping("/busqueda")
    public String busqueda(ModelMap modelo) {

        List<Oficio> oficios = oficioServicio.listarOficios();
        modelo.addAttribute("oficios", oficios);

        return "filtrosBusqueda.html";
    }

    @PostMapping("/buscarPorOficio")
    public String buscarPorOficio(String denominacion, String filtro, ModelMap modelo) {

        List<Oficio> oficios = oficioServicio.listarOficios();
        modelo.addAttribute("oficios", oficios);


        try {
            List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorParametro(denominacion, filtro);
            modelo.addAttribute("proveedores", proveedores);

        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());

        }
        return "/filtrosBusqueda.html";
    }

    @PostMapping("/buscarPorProveedor")
    public String buscarPorNombre(String nombre, ModelMap modelo) {

        List<Oficio> oficios = oficioServicio.listarOficios();
        modelo.addAttribute("oficios", oficios);

        try {
            List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorNombre(nombre);
            modelo.addAttribute("proveedores", proveedores);
            return "/filtrosBusqueda.html";

        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());

            return "/filtrosBusqueda.html";
        }

    }

    @GetMapping("/enConstruccion")
    public String enConstruccion() {
        return "pagEnConstruccion.html";
    }
}