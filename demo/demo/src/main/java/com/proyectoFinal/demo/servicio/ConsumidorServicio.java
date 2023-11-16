
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
   private ImagenServicio imagenServicio;
   
   @Transactional
   public void crearConsumidor(String pedido) throws MiException { 
       Consumidor consumidor = new Consumidor();   
   }
   
   public void crearPedido (Consumidor consumidor, String pedido) throws MiException {
       validar (pedido);
       consumidor.setPedido(pedido);
   }
   
   public List<Consumidor> listarConsumidores(){
       List<Consumidor> consumidores = new ArrayList();
       
       consumidores = ConsumidorRepositorio.findAll();
       
       return consumidores;
   }
   
   public void validar(String pedido)throws MiException {
       
       if(pedido.isEmpty() || pedido == null){
           throw new MiException ("el pedido no puede ser nulo o estar vacio");
       }
   }
}
