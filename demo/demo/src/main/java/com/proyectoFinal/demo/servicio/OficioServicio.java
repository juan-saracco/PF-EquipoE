package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.OficioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Maresca
 */
@Service
public class OficioServicio {
    
    @Autowired
    OficioRepositorio oficioRepositorio;
    
    @Transactional
    public void crearOficio(String denominacion) throws MiException{
        
        validar(denominacion);
        
        Oficio oficio = new Oficio();
        oficio.setDenominacion(denominacion);
        
        oficioRepositorio.save(oficio);        
    }
    
    public List<Oficio> listarOficios(){
        
        List<Oficio> oficios = new ArrayList();
        oficios = oficioRepositorio.findAll();
        
        return oficios;
    }
    
    @Transactional
    public void modificarOficio(String id, String denominacion) throws MiException{
        
        validar(denominacion);
        
        Optional<Oficio> rta = oficioRepositorio.findById(id);
        
        if(rta.isPresent()){
            Oficio oficio = rta.get();
            oficio.setDenominacion(denominacion);
            
            oficioRepositorio.save(oficio);
        }
    }
    
    public void eliminarOficio(String id){
    
        Optional<Oficio> rta = oficioRepositorio.findById(id);
        
        if(rta.isPresent()){
            Oficio oficio = rta.get();
            
            oficioRepositorio.delete(oficio);
        }
    }
    
    public void validar(String denominacion) throws MiException{
        
        if (denominacion == null || denominacion.isEmpty()) {
            throw new MiException("La denominacion no puede ser nula ni estar vacía");
        }
    }
    
    public Oficio getReferenceById(String id){
        return oficioRepositorio.getReferenceById(id);
    }  
    
}
