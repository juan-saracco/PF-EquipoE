package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.FeedBackServicio;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class feedBackControlador {
    @Autowired
    PedidoServicio pedidoServicio;


    @Autowired
    FeedBackServicio feedBackservicio;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listaTodos")
    public String listarFeedbackssAdmin(ModelMap modelo, @ModelAttribute("exi") String ex) {

        List<FeedBack> feedBacks = feedBackservicio.listarFeedBacks();
        modelo.addAttribute("feedbacks", feedBacks);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listaFeedbackAdmin.html";
    }
@PreAuthorize("hasRole('ROLE_ADMIN')")
@GetMapping("/restringirfeedback/{id}")
public String restringirfeedback(@PathVariable String id) throws MiException {
    feedBackservicio.restringirFeedBack(id);

    return "redirect:/feedback/listaTodos";
}
}
/*

package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.FeedBackServicio;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import java.util.List;
import javax.servlet.http.HttpSession;

import com.proyectoFinal.demo.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedBack")
public class feedBackControlador {

    @Autowired
    FeedBackServicio feedBackServicio;

    @Autowired
    PedidoServicio pedidoServicio;

    @Autowired
    ProveedorServicio proveedorservicio;

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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping ("/crear/{idProveedor}")
    public String crearFeedBack(@PathVariable String idProveedor, ModelMap modelo, HttpSession session) throws MiException {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        Proveedor proveedor = proveedorservicio.buscarPorId(idProveedor);
        modelo.addAttribute("proveedor", proveedor);

        return "feedBackForm.html";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/creado")
    public String feedBackCreado(String id, Integer calificacion, String comentario, ModelMap modelo){

        try{
            feedBackServicio.crearFeedBack(id, calificacion, comentario);
            modelo.addAttribute("Exito", "FeedBack creado correctamente.");


        }catch (MiException ex){
            modelo.addAttribute("error", ex.getMessage());
            return "feedBack_form.html";
        }
        return "feedBack_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/moderar")
    public String moderarFeedBack(String id, String idPedido, int calificacion, String comentario, ModelMap modelo){

        try{
            feedBackServicio.moderarFeedBack(id, calificacion, comentario);
            modelo.addAttribute("Exito", "El FeedBack se moderó correctamente.");

        }catch (MiException ex){
            modelo.addAttribute("error", ex.getMessage());
            return "feedBack_form.html";
        }
        return "feedBack_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/modificar")
    public String modificarFeedBack(String id, int calificacion, String comentario, ModelMap modelo)throws MiException{

        try{
         //   feedBackServicio.modificarFeedBack(id, calificacion, comentario);
            modelo.addAttribute("Exito", "El FeedBack se modificó correctamente.");

        }catch (MiException ex){
            modelo.addAttribute("error", ex.getMessage());
            return "feedBack_form.html";
        }
        return "feedBack_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/eliminar")
    public String eliminarFeedBack(@RequestParam String id, ModelMap modelo)throws MiException{

        try{
            feedBackServicio.eliminarFeedBack(id);
            modelo.addAttribute("Exito", "El FeedBack se eliminó correctamente.");

        }catch (MiException e){
            modelo.addAttribute("error", e.getMessage());
            return "feedBack_form.html";
        }
        return "feedBack_form.html";
    }

}*/

// Controlador MARI

/*
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.FeedBackServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/feedBack")
public class feedBackControlador {

    @Autowired
    private FeedBackServicio feedBackServicio;

    @GetMapping("/feedBackUsuario")
    public String feedBackUsuario(){
        return "feedBackUsuario.html";
    }

    @PostMapping("/crearFeedBack")
    public String crearFeedBack(ModelMap ModeloFeedBack, @RequestParam Integer calificacion, @RequestParam String feedback){
        try{
            feedBackServicio.crearFeedBack(calificacion, feedback);
            ModeloFeedBack.addAttribute("Exito", "FeedBack creado correctamente.");
            return "inicio.html";
        }catch (MiException e){
            List<FeedBack> feedBacks = feedBackServicio.listarFeedBacks();
            ModeloFeedBack.addAttribute("feedBacks", feedBacks);
            ModeloFeedBack.put("Error", e.getMessage());

        }
        return "feedBackUsuario";
    }


}*/