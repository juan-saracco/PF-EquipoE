package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.FeedBackServicio;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class feedBackControlador {
    @Autowired
    PedidoServicio pedidoServicio;


    @Autowired
    FeedBackServicio feedBackservicio;


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public String calificarPedido(@PathVariable String id, ModelMap modelo) {

        Pedido pedido = pedidoServicio.buscarPedidoporID(id);
        modelo.addAttribute("pedido", pedido);

        return "feedbackForm.html";
    }
    @PreAuthorize("hasRole('ROLE_PROVEEDOR')")
    @GetMapping("/mostrarcalificacion/{id}")
    public String mostrarcalificacion(@PathVariable String id, ModelMap modelo) {

       FeedBack feedback = feedBackservicio.buscarFeedbackPorIdPedido(id);
        modelo.addAttribute("feedback", feedback);

        return "verfeedbackForm.html";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/creado")
    public String pedidoCalificado(String id, Integer calificacion, String comentario, RedirectAttributes redirectAttributes) throws MiException {

        try {
            feedBackservicio.crearFeedBack(id, calificacion, comentario);
            redirectAttributes.addFlashAttribute("Exito", "Se califico el pedido correctamente");
            return "redirect:/pedido/listarpedidosUsuario";
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("Error", "Error: No se pudo calificar el pedido.");
            redirectAttributes.addFlashAttribute("calificacion", calificacion);
            redirectAttributes.addFlashAttribute("comentario", comentario);

            return "feedbackForm.html";
        }
    }

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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/verifFeedback/{id}")
    public String verifFeedback(@PathVariable String id, ModelMap modelo) {

        feedBackservicio.buscarFeedbackPorIdPedido(id);

        return "feedbackForm.html";

    }
}
