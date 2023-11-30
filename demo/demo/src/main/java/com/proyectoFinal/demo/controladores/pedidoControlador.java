
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pedidoUsuario")
public class pedidoControlador {
    
    @Autowired
    private PedidoServicio pedidoservicio;
    
    @GetMapping("/verPedido")
    public String verPedido(ModelMap modelo){
        
        List<Pedido> pedidos = pedidoservicio.listarPedidos();
        modelo.addAttribute("pedidos", pedidos);
        
        return ".html";
    }
    
    @PostMapping("/crearPedido")
    public String crearPedido(@RequestParam String id, @RequestParam Usuario consumidor, @RequestParam Proveedor proveedor, @RequestParam String solicitud, @RequestParam Boolean estadoPedido, @RequestParam Double cotizacion, @RequestParam FeedBack feedBack, @RequestParam Date alta, ModelMap ModeloPedido){
        
        try{
            pedidoservicio.crearPedido(id, id, id, id, Double.NaN);
            ModeloPedido.addAttribute("Exito", "Pedido creado correctamente.");
            return "inicio.html";
        }catch (MiException e) {
            List<Pedido> pedidos = pedidoservicio.listarPedidos();
            ModeloPedido.addAttribute("pedidos", pedidos);
            ModeloPedido.put("Error", e.getMessage());
        }
        return "inicio.html";
    }
    
    @PostMapping("/responderPedido")
    public String responderPedido(@RequestParam String id, @RequestParam Usuario consumidor, @RequestParam Proveedor proveedor, @RequestParam String solicitud, @RequestParam Boolean estadoPedido, @RequestParam Double cotizacion, @RequestParam FeedBack feedBack, @RequestParam Date alta, ModelMap ModeloPedido){
        
        try{
            pedidoservicio.crearPedido(id, id, id, id, Double.NaN);
            ModeloPedido.addAttribute("Exito", "Pedido creado correctamente.");
            return "inicio.html";
        }catch (MiException e) {
            List<Pedido> pedidos = pedidoservicio.listarPedidos();
            ModeloPedido.addAttribute("pedidos", pedidos);
            ModeloPedido.put("Error", e.getMessage());
        }
        return "inicio.html";
        
    }
    
}
    
