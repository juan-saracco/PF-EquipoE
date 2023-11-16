package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Oficios;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.ProveedorRepositorio;
import com.proyectoFinal.demo.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProveedorServicio extends Usuario {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    public ProveedorServicio() {
        super();
    }

    @Transactional
    public void registrar(String nombre, String apellido, String email, String password, String DNI, String telefono, String direccion, MultipartFile foto, Oficios oficio, String descripcion, Integer tarifaPorHora)
            throws MiException {

        validar(nombre, apellido, email, password, DNI, telefono, direccion, oficio, descripcion, tarifaPorHora);

      //  Usuario proveedor = usuarioRepositorio.findById(id).get();

       Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setDNI(DNI);
        proveedor.setDireccion(direccion);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);
        proveedor.setOficio(oficio);
        proveedor.setDescripcion(descripcion);
        proveedor.setTarifaPorHora(tarifaPorHora);


        proveedorRepositorio.save(proveedor);

    }

     public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedores = new ArrayList();

        proveedores = ProveedorRepositorio.findAll();

        return proveedores;

}
    private void validar(String nombre, String apellido, String email, String password, String password2, String DNI, String telefono, String direccion, Oficios oficio, String descripcion, Integer tarifaPorHora) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede ser nulo o estar vacia y no puede tener menos de 5 digitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas no pueden ser diferentes");

        }

        if (DNI.isEmpty() || DNI == null) {
            throw new MiException("El DNI no puede ser nulo o estar vacio");
        }

        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }

        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("La direccion no puede ser nulo o estar vacio");
        }

        if (oficio == null) {
            throw new MiException("El oficio no puede ser nulo o estar vacio");
        }


        if (tarifaPorHora.equals(0) || tarifaPorHora == null) {
            throw new MiException("La tarifa no puede ser nula o estar vacia");
        }


    }
}

