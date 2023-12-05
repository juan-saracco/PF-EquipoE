package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.OficioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/oficios")
public class oficioControlador {

    @Autowired
    private OficioServicio oficioServicio;

    @GetMapping("/lista") //MUESTRA SOLO LOS OFICIOS ACTIVOS (ESTADO: TRUE) ->SIRVE PARA LISTAR LOS OFICIOS PARA LOS USUARIOS
    public String listar(ModelMap modelo, @ModelAttribute("exi") String ex) {
        List<Oficio> oficios = oficioServicio.listarOficios();
        modelo.addAttribute("oficios", oficios);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listaOficios.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROVEEDOR','ROLE_ADMIN')")
    @GetMapping("/listaTodos") //MUESTRA TODOS LOS OFICIOS, TANTO ACTIVOS COMO DADOS DE BAJA ->SIRVE PARA MOSTRAR LOS OFICIOS AL ADMINISTRADOR
    public String listarTodos(ModelMap modelo, @ModelAttribute("exi") String ex) {
        List<Oficio> oficios = oficioServicio.listarTodosOficios();
        modelo.addAttribute("oficios", oficios);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listaOficios.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROVEEDOR','ROLE_ADMIN')")
    @GetMapping("/registrarOficios")
    public String registrarOficio() {
        return "registroOficio.html";
    }

    @PostMapping("/registroOficio")
    public String registroOficio(String denominacion, RedirectAttributes redi, @ModelAttribute("Err") String err) {

        try {
            oficioServicio.crearOficio(denominacion);
            redi.addFlashAttribute("Exito", "El oficio ha sido cargado con éxito");
            return "redirect:/oficios/listaTodos";

        } catch (MiException ex) {
            redi.addFlashAttribute("Error", ex.getMessage());

            return "redirect:/oficios/listaTodos";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROVEEDOR','ROLE_ADMIN')")
    @GetMapping("/desactReactOficios/{id}")
    public String estadoOficio(@PathVariable String id) {
        oficioServicio.cambiarEstadoOficio(id);

        return "redirect:/oficios/listaTodos";
    }

    @GetMapping("/modificarOficios/{id}")
    public String editarOficio(@PathVariable String id, ModelMap modelo, @ModelAttribute("Err") String err) {

        modelo.put("oficio", oficioServicio.getReferenceById(id));

        if (err != null) {
            modelo.put("Err", err);
        }
        return "modificarOficio.html";
    }

    @PostMapping("/editorOficio/{id}")
    public String editorOficio(@PathVariable String id, String denominacion, RedirectAttributes redi) {

        try {
            oficioServicio.modificarOficio(id, denominacion);
            redi.addFlashAttribute("Exito", "El oficio ha sido editado con éxito");

            return "redirect:../listaTodos";
        } catch (MiException ex) {
            redi.addFlashAttribute("Error", ex.getMessage());

            return "redirect:../modificarOficios/{id}";
        }
    }
}
