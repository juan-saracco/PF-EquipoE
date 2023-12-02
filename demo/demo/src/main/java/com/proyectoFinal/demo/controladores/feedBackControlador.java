
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.FeedBackServicio;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedBack")
public class feedBackControlador {
    
    @Autowired
    FeedBackServicio feedBackServicio;
    
    @Autowired
    PedidoServicio pedidoServicio;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar")
    public String listar(ModelMap modelo, @ModelAttribute("exi") String ex) {
        List<FeedBack> feedBacks = feedBackServicio.listarFeedBacks();
        modelo.addAttribute("feedBacks", feedBacks);
        
        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listarFeedBacks.html";
    }
 
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping ("/crear")
    public String crearFeedBack(ModelMap modelo, HttpSession session){
        
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);

        return "feedBack_form.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/creado/{idPedido}")
    public String feedBackCreado(String id, String idPedido, int calificacion, String comentario, ModelMap modelo){
        
        try{
            feedBackServicio.crearFeedBack(calificacion, comentario);
            modelo.addAttribute("Exito", "FeedBack creado correctamente.");
            
            
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "feedBack_form.html";
        }
        return "feedBack_form.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/moderar}")
    public String moderarFeedBack(String id, String idPedido, int calificacion, String comentario, ModelMap modelo){
        
        try{
            feedBackServicio.moderarFeedBack(id, calificacion, comentario);
            modelo.addAttribute("Exito", "El FeedBack se moderó correctamente.");
            
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "feedBack_form.html";
        }
        return "feedBack_form.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/modificar")
    public String modificarFeedBack(String id, int calificacion, String comentario, ModelMap modelo){
        
        try{
            feedBackServicio.modificarFeedBack(id, calificacion, comentario);
            modelo.addAttribute("Exito", "El FeedBack se modificó correctamente.");
            
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "feedBack_form.html";
        }
        return "feedBack_form.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/eliminar")
    public String eliminarFeedBack(String id, int calificacion, String comentario, ModelMap modelo){
        
        try{
            feedBackServicio.eliminarFeedBack(id, calificacion, comentario);
            modelo.addAttribute("Exito", "El FeedBack se eliminó correctamente.");
            
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "feedBack_form.html";
        }
        return "feedBack_form.html";
    }
    
}