
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import com.proyectoFinal.demo.servicio.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PedidoServicio pedidoServicio;
    
    @Autowired 
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    
    @GetMapping("/listaTodos") //MUESTRA TODOS LOS PEDIDOS, TANTO ACTIVOS COMO DADOS DE BAJA ->SIRVE PARA MOSTRAR LOS PEDIDOS AL ADMINISTRADOR
    public String listarTodos(ModelMap modelo, @ModelAttribute("exi") String ex) {
        
        List<Pedido> pedidos = pedidoServicio.listarPedidos();
        
        modelo.addAttribute("pedidos", pedidos);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listaPedidos.html";
    }
    
    @GetMapping("/crear/{idConsumidor}/{idProveedor}")
    public String crearPedido(
            @PathVariable String idConsumidor, 
            @PathVariable String idProveedor,
            ModelMap modelo){
        
        modelo.addAttribute("idConsumidor", idConsumidor);
        modelo.addAttribute("idProveedor", idProveedor);
        
        return "pedido_form.html";
    }
    
    @PostMapping("/enviar/{idConsumidor}/{idProveedor}")
    public String crearPedido(
            @PathVariable String idConsumidor, 
            @PathVariable String idProveedor, 
            @RequestParam String solicitud,
            ModelMap modelo){
        
        try{
            
            pedidoServicio.crearPedido(idConsumidor, idProveedor, solicitud);
            
            modelo.put("exito", "pedido creado correctamente");  
            
        }catch (MiException ex){ 
            
            modelo.put("error", ex.getMessage());
            return "pedido_form.html";
        }
        return "index.html";
    }
    
}  