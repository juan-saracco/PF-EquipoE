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
        pedido.setUsuario(usuarioRepositorio.buscarPorId(idConsumidor));
        pedido.setProveedor(proveedorRepositorio.buscarPorId(idProveedor));
        pedido.setSolicitud(solicitud);
        pedido.setEstadoPedido(true);
        pedido.setFinalizado(false);

        pedidoRepositorio.save(pedido);

    }
    
   /* public void responderPedido(String id, String idConsumidor, String idProveedor, String solicitud,Double cotizacion) throws MiException{

        validarRespuesta(idConsumidor, idProveedor, solicitud, cotizacion);

        Pedido pedido = new Pedido();

        pedido.setAlta(new Date());
        pedido.setConsumidor(usuarioRepositorio.findById(idConsumidor).get());
        pedido.setProveedor(proveedorRepositorio.findById(idProveedor).get());
        pedido.setCotizacion(cotizacion);
        pedido.setSolicitud(solicitud);
        pedido.setFinalizado(Boolean.FALSE);
        pedido.setEstadoPedido(true);

        pedidoRepositorio.save(pedido);
    }*/

    public List<Pedido> listarPedidos(){
        List<Pedido> pedidos = new ArrayList();

        pedidos = pedidoRepositorio.findAll();
        return pedidos;
    }

    public List<Pedido> listarPedidosUsuario(String id){
        List<Pedido> pedidos = pedidoRepositorio.buscarPorIdUsuario(id);
        return pedidos;
    }

    public List<Pedido> listarPedidosProveedor(String id){
        List<Pedido> pedidos = pedidoRepositorio.buscarPorIdProveedor(id);
        return pedidos;
    }

    @Transactional
    public void modificarPedido(String id, String idConsumidor, String idProveedor, String solicitud) throws MiException {

        validar(idConsumidor, idProveedor, solicitud);

        Optional<Pedido> respuesta = pedidoRepositorio.findById(id);

        if( respuesta.isPresent()){

            Pedido pedido = respuesta.get();

            pedido.setUsuario(usuarioRepositorio.buscarPorId(idConsumidor));
            pedido.setProveedor(proveedorRepositorio.buscarPorId(idProveedor));
            pedido.setSolicitud(solicitud);
            pedido.setFechamodificacion(new Date());

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

    @Transactional
    public void cambiaraceptado(String id) throws MiException {

        Optional<Pedido> respuesta = pedidoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Pedido p = respuesta.get();
            p.setFinalizado(!p.getFinalizado());
        }

        if (!respuesta.isPresent()) {
            throw new MiException("Usuario no encontrado por Id" + id);
        }
    }

      public void validar(String idConsumidor, String idProveedor, String solicitud)throws MiException {

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
    

    
    public Pedido getReferenceById(String id){
        return pedidoRepositorio.getReferenceById(id);
    }
    
    /*private void validarRespuesta(String idConsumidor, String idProveedor, String solicitud, Double cotizacion) throws MiException {
        if(idConsumidor.isEmpty() || idConsumidor == null){
            throw new MiException ("la solicitud no puede ser nula o estar vacia");
        }
        if(idProveedor.isEmpty() || idProveedor == null){
            throw new MiException ("la solicitud no puede ser nula o estar vacia");
        }
        if(solicitud.isEmpty() || solicitud == null){
            throw new MiException ("la solicitud no puede ser nula o estar vacia");
        }
        if(cotizacion.equals(0) || cotizacion == null){
            throw new MiException ("la solicitud no puede ser nula o estar vacia");
        }
    }*/
    }

