package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.PedidoRepositorio;
import com.proyectoFinal.demo.repositorios.ProveedorRepositorio;
import com.proyectoFinal.demo.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServicio {

    @Autowired
    PedidoRepositorio pedidoRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    ProveedorRepositorio proveedorRepositorio;

    @Transactional
    public void crearPedido(String idConsumidor, String idProveedor, String solicitud) throws MiException{

        validar(idConsumidor, idProveedor, solicitud);

        Pedido pedido = new Pedido();

        pedido.setAlta(new Date());
        pedido.setConsumidor(usuarioRepositorio.findById(idConsumidor).get());
        pedido.setProveedor(proveedorRepositorio.findById(idProveedor).get());
        pedido.setSolicitud(solicitud);
        pedido.setEstadoPedido(true);

        pedidoRepositorio.save(pedido);
    }
    
    public List<Pedido> listarPedidos(){
        List<Pedido> pedidos = new ArrayList();

        pedidos = pedidoRepositorio.findAll();
        return pedidos;
    }

    @Transactional
    public void modificarPedido(String id, String idConsumidor, String idProveedor, String solicitud) throws MiException {
        validar(idConsumidor, idProveedor, solicitud);

        Optional<Pedido> respuesta = pedidoRepositorio.findById(id);

        if( respuesta.isPresent()){

            Pedido pedido = respuesta.get();

            pedido.setConsumidor(usuarioRepositorio.findById(idConsumidor).get());
            pedido.setProveedor(proveedorRepositorio.findById(idProveedor).get());
            pedido.setSolicitud(solicitud);
            pedido.setFecha_modificacion(new Date());

            pedidoRepositorio.save(pedido);
        }
    }

    @Transactional
    public void cambiarestado(String id) throws MiException {

        Optional<Pedido> respuesta = pedidoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Pedido p = respuesta.get();
            p.setEstadoPedido(!p.getEstadoPedido());
        }

        if (!respuesta.isPresent()) {
            throw new MiException("Usuario no encontrado por Id" + id);
        }
    }
    
    private void validar(String idConsumidor, String idProveedor, String solicitud)throws MiException {

        if(idConsumidor.isEmpty() || idConsumidor == null){
            throw new MiException ("la solicitud no puede ser nula o estar vacia");
        }
        if(idProveedor.isEmpty() || idProveedor == null){
            throw new MiException ("la solicitud no puede ser nula o estar vacia");
        }
        if(solicitud.isEmpty() || solicitud == null){
            throw new MiException ("la solicitud no puede ser nula o estar vacia");
        }
    }
    }

