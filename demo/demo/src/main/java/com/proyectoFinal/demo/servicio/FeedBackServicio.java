
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

@Service
public class FeedBackServicio {

    @Autowired
    FeedbackRepositorio feedbackRepositorio;

    @Autowired
    PedidoServicio pedidoservicio;

    @Transactional
    public void crearFeedBack(String idPedido, Integer calificacion, String comentario) throws MiException {

        validar(calificacion, comentario);

        FeedBack feedBack = new FeedBack();

        Pedido pedido = pedidoservicio.buscarPedidoporID(idPedido);

        feedBack.setPedido(pedido);
        feedBack.setCalificacion(calificacion);
        feedBack.setComentario(comentario);
        feedBack.setAlta(new Date());
        feedBack.setEstado(true);

        feedbackRepositorio.save(feedBack);
    }

    public List<FeedBack> listarFeedBacks() {
        List<FeedBack> feedback = feedbackRepositorio.findAll();
        return feedback;
    }

    public List<FeedBack> listarFeedBacksActivos() {
        List<FeedBack> feedBacks = feedbackRepositorio.listarFeedbacksActivos();

        return feedBacks;
    }

    public  FeedBack buscarFeedbackPorIdPedido(String id){

        FeedBack feedback = feedbackRepositorio.buscarFeedbackPorId(id);

        return feedback;
    }

    @Transactional
    public void editarFeedBack(String id, Integer calificacion, String comentario) throws MiException {

        validar(calificacion, comentario);

        Optional<FeedBack> respuesta = feedbackRepositorio.findById(id);

        if (respuesta.isPresent()) {

            FeedBack feedBack = respuesta.get();
            feedBack.setCalificacion(calificacion);
            feedBack.setComentario(comentario);

            feedbackRepositorio.save(feedBack);
        }
    }

    public void moderarFeedBack(String id, Integer calificacion, String comentario) throws MiException {

        validar(calificacion, comentario);

        Optional<FeedBack> rta = feedbackRepositorio.findById(id);

        if (rta.isPresent()) {

            FeedBack feedBack = rta.get();
            feedBack.setCalificacion(feedBack.getCalificacion());
            feedBack.setComentario(comentario);

            feedbackRepositorio.save(feedBack);
        }
    }

    @Transactional
    public void restringirFeedBack(String id)throws MiException  {

        Optional<FeedBack> rta = feedbackRepositorio.findById(id);

        if (rta.isPresent()) {
            FeedBack feedBack = rta.get();
            feedBack.setEstado(!feedBack.getEstado());
        }
        if (!rta.isPresent()) {
            throw new MiException("Feedback no encontrado por Id" + id);
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
