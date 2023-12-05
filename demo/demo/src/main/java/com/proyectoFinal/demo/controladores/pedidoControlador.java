
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.servicio.FeedBackServicio;
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

    @Autowired
    FeedBackServicio feedBackservicio;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listaTodos")
    //MUESTRA TODOS LOS PEDIDOS, TANTO ACTIVOS COMO DADOS DE BAJA ->SIRVE PARA MOSTRAR LOS PEDIDOS AL ADMINISTRADOR
    public String listarPedidosAdmin(ModelMap modelo, @ModelAttribute("exi") String ex) {

        List<Pedido> pedidos = pedidoServicio.listarPedidos();
        modelo.addAttribute("pedidos", pedidos);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listaPedidosAdmin.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
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

            return "listapedidosUsuario.html";
        }
        return "redirect:/pedido/listarpedidosUsuario";
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/modificar/{id}")
    public String modificarPedido(@PathVariable String id,String solicitud, ModelMap modelo) {

        Pedido pedido = pedidoServicio.buscarPedidoporID(id);
        modelo.addAttribute("pedido", pedido);

        return "Modificarpedido_form.html";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/modificado")
    public String modificado(String id, String solicitud,ModelMap modelo, RedirectAttributes redirectAttributes ){

        try {

            pedidoServicio.modificarPedido(id, solicitud);
            redirectAttributes.addFlashAttribute("Exito", "Pedido editada correctamente");

        } catch (MiException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("solicitud", "La solicitud no puede estar vacia");

            return "Modificarpedido_form.html";
        }
        return "redirect:/pedido/listarpedidosUsuario";
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/listarpedidosUsuario")
    public String listarPedidosUsuario(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
         modelo.put("usuario", usuario);

        List<Pedido> pedidosUsuario = pedidoServicio.listarPedidosUsuario(usuario.getId());
        modelo.put("pedidos", pedidosUsuario);

        return "listaPedidosUsuario.html";
    }

    @PreAuthorize("hasRole('ROLE_PROVEEDOR')")
    @GetMapping("/listarpedidosProveedor")
    public String listarPedidosProveedor(ModelMap modelo, HttpSession session) {

        Proveedor proveedor = (Proveedor) session.getAttribute("usuariosession");
        modelo.put("proveedor", proveedor);

        List<Pedido> pedidosProveedor = pedidoServicio.listarPedidosProveedor(proveedor.getId());
        modelo.put("pedidos", pedidosProveedor);

        return "listaPedidosProveedor.html";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/aceptadoRechazadoPedido/{id}")
    public String estadoPedido(@PathVariable String id) throws MiException {
        pedidoServicio.cambiarestado(id);

    return "redirect:/pedido/listarpedidosUsuario";
    }


    @PreAuthorize("hasRole('ROLE_PROVEEDOR')")
    @GetMapping("/finalizarPedido/{id}")
    public String finalizarPedido(@PathVariable String id) throws MiException {
        pedidoServicio.cambiarFinalizado(id);

        return "redirect:/pedido/listarpedidosProveedor";
    }

}