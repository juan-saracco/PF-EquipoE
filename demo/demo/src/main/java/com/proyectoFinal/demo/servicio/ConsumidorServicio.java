
package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Consumidor;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.ConsumidorRepositorio;
import com.proyectoFinal.demo.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorServicio {
    
   @Autowired
   private UsuarioRepositorio usuarioRepositorio;
   
   @Autowired
   private ConsumidorRepositorio consumidorRepositorio;
   
   @Autowired
   private PedidoServicio pedidoServicio;
   
   @Autowired
   private ImagenServicio imagenServicio;
   
   @Transactional
   public void crearConsumidor() throws MiException { 
       
       Consumidor consumidor = new Consumidor();
       
       consumidorRepositorio.save(consumidor);
   }
   
   public List<Consumidor> listarConsumidores(){
       
       List<Consumidor> consumidores = new ArrayList();
       
       consumidores = consumidorRepositorio.findAll();
       
       return consumidores;
   }
   
   //Como relacionar pedido con consumidor, proveedor y a su vez si el pedido deberia ser o no un atributo de consumidor o al reves. 
   
    @Transactional(readOnly = true)
    public consumidor getOne(String id){
        return consumidorRepositorio.getOne(id);
    }
    
    //cambiar el estado no eliminar
    @Transactional
    public void eliminar(String id) throws MiException{
        
        Consumidor consumidor = consumidorRepositorio.getById(id);
        
        consumidorRepositorio.delete(consumidor);

    }
    
    public void validar() throws MiException{ 
        
        if(.isEmpty() ||  == null){
            throw new MiException(" no puede ser nulo o estar vacio");
        }
    }    
   
   
}
