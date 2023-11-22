package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.OficioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminControlador {
    
    @Autowired
    private OficioServicio oficioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panelAdmin.html";
    }

    @GetMapping("/oficios")
    public String panelOficios(){
        return "listaOficios.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo, @ModelAttribute("exi") String ex){
        List<Oficio> oficios = oficioServicio.listarOficios();
        modelo.addAttribute("oficios", oficios);
        
        if(ex != null){
            modelo.put("exi", ex);
        }
        
        return "listaOficio.html";
    }
    
    @GetMapping("/registrarOficios")
    public String registrarOficio(){
        return "registroOficio.html";
    }
    
    @PostMapping("/registroOficio")
    public String registroOficio(String denominacion, ModelMap modelo){
        
        try {
            oficioServicio.crearOficio(denominacion);
            modelo.put("exito","El oficio ha sido cargado con éxito");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            
            return "registroOficio.html";
        }
        return "listaOficios.html";
    }
    
    @GetMapping("/desactReactOficios")
    public String estadoOficio(String id) {
        oficioServicio.cambiarEstadoOficio(id);
        
        return "listaOficios.html";
    }
    
    @GetMapping("/modificarOficios")
    public String editarOficio() {
        
        return "editarOficio.html";
    }
    
    @PostMapping("/editorOficio")
    public String editorOficio(String id, String denominacion, ModelMap modelo){
        
        try {
            oficioServicio.modificarOficio(id, denominacion);
            modelo.put("exito","El oficio ha sido editado con éxito");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            
            return "editarOficio.html";
        }
        return "listaOficios.html";
    }
}
