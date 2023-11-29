package com.proyectoFinal.demo.controladores;


import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.OficioServicio;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import com.proyectoFinal.demo.servicio.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    public OficioServicio oficioServicio;
    
    @Autowired
    public ProveedorServicio proveedorServicio;


/*Pagina de presentacion inicial accesible para todos*/
    @GetMapping("/")
    public String inicio() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false)String error, ModelMap mod) {
        if(error != null){
            mod.put("Error","Usuario o contraseña incorrectos");
        }
        return "login.html";
    }

    /*Pagina inicial solo accesible para usuarios consumidores*/
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicioAdmin(HttpSession session){

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuariosession");
        Proveedor proveedorLogueado = (Proveedor) session.getAttribute("proveedorsession");

//        if(usuarioLogueado.getRol().toString().equals("USER") ){
//            return "redirect:/user/dashboard";
//        }
//        if(usuarioLogueado.getRol().toString().equals("PROVEEDOR") ){
//            return "redirect:/proveedor/dashboard";
//        }

        if(usuarioLogueado.getRol().toString().equals("ADMIN") ){
           return "redirect:/admin/dashboard";
        }

        return "redirect:/";
    }

    @GetMapping("/busqueda")
    public String busqueda(ModelMap modelo) {
        List<Oficio> oficios = oficioServicio.listarOficios();
        modelo.addAttribute("oficios", oficios);

        return "filtrosBusqueda.html";
    }

    @PostMapping("/buscarPorOficio")
    public String buscar(@RequestParam String denominacion, String filtro, ModelMap modelo, @ModelAttribute("Error") String error){
        try {
            proveedorServicio.listarProveedoresPorParametro(denominacion, filtro);
            
        } catch (Exception e) {
             List<Oficio> oficios = oficioServicio.listarOficios();
             modelo.addAttribute("oficios", oficios);
             
             modelo.put("Error", e.getMessage());
             modelo.put("denominacion", denominacion);
             modelo.put("filtro", filtro);
             
             
        }
        return "filtrosBusqueda.html";
    }


    @PostMapping("/buscarPorProveedor")
    public String buscar(@RequestParam String denominacion){
        return "";
    }

}







