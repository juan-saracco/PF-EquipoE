
package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.FeedBack;
import com.proyectoFinal.demo.excepciones.MiException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyectoFinal.demo.repositorios.FeedBackRepositorio;

@Service
public class FeedBackServicio {

    @Autowired
    FeedBackRepositorio feedBackRepositorio;
    
    @Transactional
    public void crearFeedBack(Integer calificacion, String comentario) throws MiException {

        validar(calificacion, comentario);

        FeedBack feedBack = new FeedBack();
        feedBack.setAlta(new Date());
        feedBack.setCalificacion(calificacion);
        feedBack.setComentario(comentario);
        feedBack.setEstado(Boolean.TRUE);

        feedBackRepositorio.save(feedBack);
    }

    public List<FeedBack> listarFeedBacks(){
        List<FeedBack> feedBacks = new ArrayList();

        feedBacks = feedBackRepositorio.listarFeedBacksActivos();

        return feedBacks;
    }

    @Transactional
    public void modificarFeedBack(String id, Integer calificacion, String comentario) throws MiException {
        
        validar(calificacion, comentario);

        Optional<FeedBack> respuesta = feedBackRepositorio.findById(id);

        if (respuesta.isPresent()) {

            FeedBack feedBack = respuesta.get();
            
            feedBack.setCalificacion(calificacion);
            feedBack.setComentario(comentario);
            feedBack.setFecha_modificacion(new Date());

            feedBackRepositorio.save(feedBack);
        }
    }

    public void moderarFeedBack(String id, Integer calificacion, String comentario) throws MiException{

        validar(calificacion, comentario);

        Optional<FeedBack> respuesta = feedBackRepositorio.findById(id);

        if (respuesta.isPresent()) {

            FeedBack feedBack = respuesta.get();
            feedBack.setCalificacion(feedBack.getCalificacion());
            feedBack.setComentario(comentario);
            feedBack.setFecha_modificacion(new Date());
            
            feedBackRepositorio.save(feedBack);
        }
    }

    public void eliminarFeedBack(String id, Integer calificacion, String comentario) throws MiException {
        
        validar(calificacion, comentario);

        Optional<FeedBack> rta = feedBackRepositorio.findById(id);

        if(rta.isPresent()){
            FeedBack feedBack = rta.get();

            if(feedBack.getEstado().equals(Boolean.TRUE)){
                feedBack.setEstado(Boolean.FALSE);
            }else if(feedBack.getEstado().equals(Boolean.FALSE)){
                feedBack.setEstado(Boolean.TRUE);
            }

            feedBackRepositorio.delete(feedBack);
        }
    }

    private void validar(Integer calificacion, String comentario) throws MiException {

        if (calificacion == null) {
            throw new MiException("la calificación no puede ser nula");
        }

        if (comentario.isEmpty() || comentario == null) {
            throw new MiException("el comentario no puede ser nulo o estar vacio");
        }
    }

}
