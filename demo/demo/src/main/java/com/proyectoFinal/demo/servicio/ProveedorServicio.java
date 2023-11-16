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
    public void registrar(MultipartFile archivo, String id, String nombre, String apellido, Integer DNI, Oficios oficio, String direccion, String email, String descripcion, Integer tarifaPorHora, String telefono, Double calificacion) throws MiException {

        validar(oficio, descripcion, tarifaPorHora, calificacion);

        Usuario proveedor = usuarioRepositorio.findById(id).get();

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
        proveedor.setCalificacion(calificacion);

        proveedorRepositorio.save(proveedor);

    }

     public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedores = new ArrayList();

        proveedores = ProveedorRepositorio.findAll();

        return proveedores;

}

}

