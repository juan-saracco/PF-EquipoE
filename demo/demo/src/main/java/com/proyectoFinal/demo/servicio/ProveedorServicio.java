package com.proyectoFinal.demo.servicio;

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

/**
 *
 * @author ILIANA
 */
@Service
public class ProveedorServicio extends Usuario {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
     
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearProveedor(MultipartFile archivo, String nombre, String apellido, Enum oficio, String descripcion, Integer tarifaPorHora, String telefono, Double calificacion) throws MiException {

        validar(oficio, descripcion, tarifaPorHora, calificacion);

        Usuario usuario = usuarioRepositorio.findById(EnumOficio).get();

        Proveedor proveedor = new Proveedor();

        proveedor.setOficio(oficio);
        proveedor.setDescripcion(descripcion);
        proveedor.setTarifaPorHora(tarifaPorHora);
        proveedor.setCalificacion(calificacion);

        proveedorRepositorio.save(proveedor);
       
    }
    
     public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedores = new ArrayList();

        proveedores = proveedorRepositorio.findAll();

        return proveedores;

}
