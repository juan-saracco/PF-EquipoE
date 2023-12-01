
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedidoUsuario")
public class pedidoControlador {
    
    @Autowired
    private PedidoServicio pedidoServicio;
    
    @GetMapping 
    public String pedidoUsuario(){
        return "pedidoUsuario";
    }
    
    @PostMapping("/crear-pedido")
    public String crearPedido(ModelMap ModeloPedido, String id, String idConsumidor, String idProveedor, String solicitud){
        
        try{
            pedidoServicio.crearPedido(id, idConsumidor, idProveedor, solicitud);
            ModeloPedido.addAttribute("Exito", "Pedido creado correctamente");
            return "pedidoUsuario";
        }catch (MiException e){
            List<Pedido> pedidos = pedidoServicio.listarPedidos();
        }
        return "pedidoUsuario";
    }
    
    /*@GetMapping ("/pedidoProveedor")
    public String pedidoProveedor(){
        return "pedidoProveedor";
    }
    
    @PostMapping("/responder-pedido")
    public String responderPedido(ModelMap ModeloPedido, String id, String idConsumidor, String idProveedor, String solicitud, Double cotizacion){
        
        try{
            pedidoServicio.responderPedido(id, idConsumidor, idProveedor, solicitud, cotizacion);
            ModeloPedido.addAttribute("Exito", "Respuesta enviada correctamente");
            return "pedidoProveedor";
        }catch (MiException e){
            List<Pedido> pedidos = pedidoServicio.listarPedidos();
        }
        return "pedidoProveedor";
    }*/
    
}  