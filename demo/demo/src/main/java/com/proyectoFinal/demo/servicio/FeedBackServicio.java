
package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.FeedbackRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author maresca
 */
@Service
public class FeedBackServicio {

    @Autowired
    private FeedbackRepositorio feedbackRepositorio;

    @Transactional
    public void crearFeedBack(Pedido pedido, Integer calificacion, String comentario) throws MiException {

        validar(pedido, calificacion, comentario);
        
        FeedBack feedBack = new FeedBack();
        
        feedBack.setPedido(pedido);
        feedBack.setCalificacion(calificacion);
        feedBack.setComentario(comentario);
        feedBack.setAlta(new Date());
        
        feedbackRepositorio.save(feedBack);
    }
    
    public List<FeedBack> listarFeedBacks(){
        List<FeedBack> feedBacks = new ArrayList();
        
        feedBacks = feedbackRepositorio.findAll();
        
        return feedBacks;
    }
    
    @Transactional
    public void editarFeedBack(String id, Pedido pedido, Integer calificacion, String comentario) throws MiException {

        validar(pedido, calificacion, comentario);

        Optional<FeedBack> respuesta = feedbackRepositorio.findById(id);

        if (respuesta.isPresent()) {

            FeedBack feedBack = respuesta.get();
            feedBack.setPedido(pedido);
            feedBack.setCalificacion(calificacion);
            feedBack.setComentario(comentario);

            feedbackRepositorio.save(feedBack);
        }
    }
    
    public void moderarFeedBack(String id, Pedido pedido, Integer calificacion, String comentario) throws MiException{
       
        validar(pedido, calificacion, comentario);
        
        Optional<FeedBack> rta = feedbackRepositorio.findById(id);
        
        if (rta.isPresent()) {

            FeedBack feedBack = rta.get();
            feedBack.setPedido(pedido);
            feedBack.setCalificacion(feedBack.getCalificacion());
            feedBack.setComentario(comentario);

            feedbackRepositorio.save(feedBack);
        }        
    }
    
    public void eliminarFeedBack(String id){
        
        Optional<FeedBack> rta = feedbackRepositorio.findById(id);
        
        if(rta.isPresent()){
            FeedBack feedBack = rta.get();
            
            feedbackRepositorio.delete(feedBack);
        }
    }
    
    private void validar(Pedido pedido, Integer calificacion, String comentario) throws MiException {

        if (pedido == null) {
            throw new MiException("el pedido no puede ser nulo");
        }

        if (calificacion == null) {
            throw new MiException("la calificación no puede ser nula");
        }

        if (comentario.isEmpty() || comentario == null) {
            throw new MiException("el comentario no puede ser nulo o estar vacio");
        }        
    }    

}
