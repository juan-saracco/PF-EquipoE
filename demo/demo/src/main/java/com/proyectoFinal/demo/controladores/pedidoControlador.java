
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import com.proyectoFinal.demo.servicio.ProveedorServicio;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedido")
public class pedidoControlador {
    @Autowired
    PedidoServicio pedidoServicio;

    @Autowired
    ProveedorServicio proveedorServicio;


    @GetMapping("/listaTodos")
    //MUESTRA TODOS LOS PEDIDOS, TANTO ACTIVOS COMO DADOS DE BAJA ->SIRVE PARA MOSTRAR LOS PEDIDOS AL ADMINISTRADOR
    public String listarTodos(ModelMap modelo, @ModelAttribute("exi") String ex) {
        List<Pedido> pedidos = pedidoServicio.listarPedidos();
        modelo.addAttribute("pedido", pedidos);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listarPedidosProveedor.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")//Esto no estaba en MARESCA
    @GetMapping("/crear/{idProveedor}")
    public String crearPedido(@PathVariable String idProveedor, ModelMap modelo, HttpSession session) throws MiException {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);

        Proveedor proveedor = proveedorServicio.buscarPorId(idProveedor);
        modelo.addAttribute("proveedor", proveedor);

        return "pedido_form.html";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/creado")
    public String pedidoCreado(String idConsumidor, String idProveedor, String solicitud, ModelMap modelo, RedirectAttributes redirectAttributes) {

        try {
            pedidoServicio.crearPedido(idConsumidor, idProveedor, solicitud);

            List<Pedido> pedidosUsuario = pedidoServicio.listarPedidosUsuario(idConsumidor);
            modelo.addAttribute("pedido", pedidosUsuario);

            redirectAttributes.addFlashAttribute("Exito", "Pedido creado correctamente");

        } catch (MiException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("solicitud", "La solicitud no puede estar vacia");

            return "listaPedidos.html";
        }
        return "listaPedidos.html";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/listarpedidos")
    public String listarPedidosUsuario(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        List<Pedido> pedidosUsuario = pedidoServicio.listarPedidosUsuario(usuario.getId());
        modelo.put("pedido", pedidosUsuario);


        return "listaPedidos.html";

    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR')")
    @GetMapping("/desactReactPedido/{id}")
    public String estadoPedido(@PathVariable String id) throws MiException {
        pedidoServicio.cambiarestado(id);

        return "redirect:/pedido/listaTodos";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR')")
    @GetMapping("/aceptadoRechazadoPedido/{id}")
    public String aceptarPedido(@PathVariable String id) throws MiException {
        pedidoServicio.cambiaraceptado(id);

        return "redirect:/pedido/listaTodos";
    }
    
}  