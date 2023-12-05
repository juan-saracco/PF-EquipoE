package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Imagen;
import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.enumeraciones.Rol;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.OficioRepositorio;
import com.proyectoFinal.demo.repositorios.ProveedorRepositorio;
import com.proyectoFinal.demo.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ProveedorServicio extends UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private OficioRepositorio oficioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombre, String apellido, String email, String password, String password2, String DNI, String telefono, String direccion, MultipartFile archivo, String denominacion, String descripcion, Double tarifaPorHora)
            throws MiException {

        Oficio oficio = oficioRepositorio.buscarOficioPorDenom(denominacion);
        Imagen foto = imagenServicio.guardar(archivo);

        validar(oficio, descripcion, tarifaPorHora);

        Proveedor proveedor = new Proveedor();
        super.registrar(nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo, proveedor);


        proveedor.setOficio(oficio);
        proveedor.setDescripcion(descripcion);
        proveedor.setTarifaPorHora(tarifaPorHora);
        proveedor.setRol(Rol.PROVEEDOR);

        proveedorRepositorio.save(proveedor);

    }

    @Transactional
    public Proveedor actualizar(String id, String nombre, String apellido, String email, String password, String password2, String DNI, String telefono, String direccion, MultipartFile archivo, String denominacion, String descripcion, Double tarifaPorHora) throws MiException {

        Oficio oficio = oficioRepositorio.buscarOficioPorDenom(denominacion);
        validar(oficio, denominacion, tarifaPorHora);

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);


        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();

            super.actualizar(id, nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo);

            proveedor.setOficio(oficio);
            proveedor.setDescripcion(descripcion);
            proveedor.setTarifaPorHora(tarifaPorHora);

            return proveedor;
        }
        return null;
    }

    public List<Proveedor> listarTodosProveedores() {
        List<Proveedor> proveedores = new ArrayList();
        proveedores = (proveedorRepositorio.findAll());
        return proveedores;
    }

    public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedores = new ArrayList();
        proveedores = proveedorRepositorio.listarProveedoresActivos();
        return proveedores;
    }

    public List<Proveedor> listarProveedoresPorParametro(String denominacion, String filtro) throws MiException {

        if (denominacion.isEmpty()) {
            throw new MiException("Debe seleccionar un oficio");
        }

        List<Proveedor> proveedores = new ArrayList();

        if (filtro.equalsIgnoreCase("nombre")) {
            proveedores = proveedorRepositorio.listarProvPorParamApellido(denominacion);
        } else if (filtro.equalsIgnoreCase("tarifa")) {
            proveedores = proveedorRepositorio.listarProvPorParamTarifa(denominacion);
        }
        if (proveedores.isEmpty() || proveedores == null) {
            throw new MiException("La búsqueda no arroja resultados");
        }
        return proveedores;
    }

    public List<Proveedor> listarProveedoresPorNombre(String nombre) throws MiException {

        if (nombre.isEmpty()) {
            throw new MiException("Debe ingresar un nombre");
        }

        String nombreAux = "%" + nombre + "%";
        List<Proveedor> proveedores = new ArrayList();

        proveedores = proveedorRepositorio.listarProvPorParamNombre(nombreAux);

        if (proveedores.isEmpty() || proveedores == null) {
            throw new MiException("La búsqueda no arroja resultados");
        }
        return proveedores;
    }

    public Proveedor buscarPorId(String id) throws MiException {
        Proveedor proveedor = new Proveedor();
        if (id.isEmpty()) {
            throw new MiException("Debe ingresar un ID");
        } else {
            proveedor = proveedorRepositorio.buscarPorId(id);
        }
        return proveedor;
    }

    @Transactional
    public void cambiarestado(String id) {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setEstado(!proveedor.getEstado());

            proveedorRepositorio.save(proveedor);
        }

    }

    @Transactional
    public void cambiarOficio(String id, String denominacion) throws MiException {

        Oficio oficio = oficioRepositorio.buscarOficioPorDenom(denominacion);

        if (oficio == null) {
            throw new MiException("El oficio no puede ser nulo o estar vacío");
        }

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setOficio(oficio);

            proveedorRepositorio.save(proveedor);
        }

    }

    private void validar(Oficio oficio, String descripcion, Double tarifaPorHora) throws MiException {

        if (oficio == null) {
            throw new MiException("El oficio no puede ser nulo o estar vacio!!");
        }
        if (descripcion.isEmpty()) {
            throw new MiException("La descripcion no puede ser nula o estar vacia");
        }

        if (tarifaPorHora == null) {
            throw new MiException("La tarifa no puede ser nula o estar vacia");
        }

    }

}


