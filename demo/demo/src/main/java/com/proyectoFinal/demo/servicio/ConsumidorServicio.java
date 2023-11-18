
package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Consumidor;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.ConsumidorRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ConsumidorServicio extends UsuarioServicio {

    @Autowired
    ConsumidorRepositorio consumidorRepositorio;

    @Transactional
    public void registrarConsumidor(MultipartFile archivo, String id,
                                    String nombre, String apellido, String DNI, String email,
                                    String direccion, String telefono, String password, String password2)
            throws MiException {

        super.registrar(archivo, id, nombre, apellido, DNI, email, direccion, telefono, password, password2);

        Consumidor consumidor = new Consumidor();

        consumidorRepositorio.save(consumidor);
    }

    public List<Consumidor> listarConsumidores(){

        List<Consumidor> consumidores = new ArrayList();

        consumidores = consumidorRepositorio.findAll();

        return consumidores;
    }

}
