
package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.excepciones.MiException;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicio {
    
    @Autowired
    ConsumidorRepositorio consumidorRepositorio;
    
    @Autowired
    ProveedorRepositorio proveedorRepositorio;
    
    @Transactional
    public void crearPedido(Consumidor consumidor, Proveedor proveedor, String solicitud){
        
    }
            
    public List<pedido> listarPedido(){
        
    }
            
    public void modificarPedido(){
        
    }
            
    public void validar(String pedido)throws MiException {
       
       if(pedido.isEmpty() || pedido == null){
           throw new MiException ("el pedido no puede ser nulo o estar vacio");
       }
   }
    
}
