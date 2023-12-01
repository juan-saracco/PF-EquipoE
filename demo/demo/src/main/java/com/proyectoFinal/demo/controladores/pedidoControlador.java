
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
    
    
    @GetMapping("/crear")
    public String crearPedido(ModelMap modelo){
        
        List<Usuario> consumidores = usuarioServicio.listarUsuarios();
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("consumidores", consumidores);
        modelo.addAttribute("proveedores", proveedores);
        return "pedido_form.html";
    }
    
    @PostMapping("/enviar")
    public String crearPedido(@RequestParam String idConsumidor,
            @RequestParam String idProveedor, @RequestParam String solicitud,ModelMap modelo){
        
        try{
            
            pedidoServicio.crearPedido(idConsumidor, idProveedor, solicitud);
            
            modelo.put("Exito", "Pedido creado correctamente");  
            
        }catch (MiException ex){ 
            modelo.put("Error", ex.getMessage());
            return "pedidoUsuario.html";
        }
        return "index.html";
    }
    
}  