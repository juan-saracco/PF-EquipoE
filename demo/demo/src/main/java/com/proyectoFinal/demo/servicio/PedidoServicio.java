
package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Consumidor;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.ConsumidorRepositorio;
import com.proyectoFinal.demo.repositorios.PedidoRepositorio;
import com.proyectoFinal.demo.repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicio {
    
    @Autowired
    PedidoRepositorio pedidoRepositorio;
    
    @Autowired
    ConsumidorRepositorio consumidorRepositorio;
    
    @Autowired
    ProveedorRepositorio proveedorRepositorio;
    
    @Transactional
    public void crearPedido(String solicitud, String idConsumidor, String idProveedor) throws MiException{
        validar(solicitud);
        
        
        /* Revisar:
        Optional<Pedido> respuesta = PedidoRepositorio.findById(idPedido);
        Optional<Consumidor> respuestaConsumidor = consumidorRepositorio.findById(idConsumidor);
        Optional<Proveedor> respuestaProveedor = proveedorRepositorio.findById(idProveedor);
        */
        
        Consumidor consumidor = consumidorRepositorio.findById(idConsumidor).get();
        Proveedor proveedor = proveedorRepositorio.findById(idProveedor).get();
        Pedido pedido = new Pedido();
        
        pedido.setSolicitud(solicitud);
        
        pedidoRepositorio.save(pedido);
    }
            
    public List<Pedido> listarPedidos(){
        List<Pedido> pedidos = new ArrayList();
        
        pedidos = pedidoRepositorio.findAll();
        return pedidos;
    }
            
    public void modificarPedido(String solicitud, String idConsumidor, String idProveedor) throws MiException {
        validar(solicitud);
        
        /* Revisar:
        Optional<Pedido> respuesta = PedidoRepositorio.findById(idPedido);
        Optional<Consumidor> respuestaConsumidor = consumidorRepositorio.findById(idConsumidor);
        Optional<Proveedor> respuestaProveedor = proveedorRepositorio.findById(idProveedor);
        */
        
        Consumidor consumidor = new Consumidor();
        Proveedor proveedor = new Proveedor();
        
        //Falta completar:
    }
            
    public void validar(String solicitud)throws MiException {
       
       if(solicitud.isEmpty() || solicitud == null){
           throw new MiException ("la solicitud no puede ser nula o estar vacia");
       }
   }
    
}
