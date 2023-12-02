
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

@Controller
@RequestMapping("/pedido")
public class pedidoControlador {
    @Autowired
    PedidoServicio pedidoServicio;
    
    @Autowired
    ProveedorServicio proveedorServicio;
    
    @GetMapping("/listaTodos") //MUESTRA TODOS LOS PEDIDOS, TANTO ACTIVOS COMO DADOS DE BAJA ->SIRVE PARA MOSTRAR LOS PEDIDOS AL ADMINISTRADOR
    public String listarTodos(ModelMap modelo, @ModelAttribute("exi") String ex) {
        List<Pedido> pedidos = pedidoServicio.listarPedidos();
        modelo.addAttribute("pedidos", pedidos);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listaPedidos.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/crear/{idProveedor}")
    public String crearPedido(@PathVariable String idProveedor, ModelMap modelo, HttpSession session){
        
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        
        /* AGREGAMOS ESTA PARTE PARA COMPROBAR QUE PROVEEDOR ES UNA INSTANCIA DE USUARIO. LO REQUIERE EL IDE */
        if (usuario instanceof Proveedor) {
        Proveedor proveedor = (Proveedor) proveedorServicio.getOne(idProveedor);
        
        modelo.addAttribute(proveedor);
        } else {
       
            return "pedido_form.html";
}
        return "pedido_form.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/creado/{idProveedor}")
    public String pedidoCreado(String id, String idConsumidor, String idProveedor, String solicitud, ModelMap modelo){
        
        try{
            pedidoServicio.crearPedido(idConsumidor, idProveedor, solicitud);
            modelo.addAttribute("Exito", "Pedido creado correctamente");
            
        }catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "pedido_form.html";
        }
        return "pedido_form.html";
    }
}  