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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ProveedorServicio extends UsuarioServicio  {

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
        
    validar(oficio, descripcion, tarifaPorHora);
    Proveedor proveedor = new Proveedor();
    super.registrar(nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo, proveedor);
    
    Imagen img = imagenServicio.guardar(archivo);

    proveedor.setImagen(img);
    proveedor.setOficio(oficio);
    proveedor.setDescripcion(descripcion);
    proveedor.setTarifaPorHora(tarifaPorHora);
    proveedor.setEstado(true);
    proveedor.setRol(Rol.PROVEEDOR);
    proveedor.setFecha_alta(new Date());

    proveedorRepositorio.save(proveedor);

}

    @Transactional
    public void actualizar(String id, String nombre, String apellido, String email, String password, String password2, String DNI, String telefono, String direccion, MultipartFile imagen, Oficio oficio, String descripcion, Double tarifaPorHora) throws MiException {
        
        validar(oficio, descripcion, tarifaPorHora);
        super.actualizar(id, nombre, apellido, email, password, password2, DNI,telefono, direccion,imagen);

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);


        if (respuesta.isPresent()) {

            super.actualizar(id, nombre, apellido, email, password, password2, DNI,telefono, direccion,imagen);

            Proveedor proveedor = new Proveedor();

            proveedor.setOficio(oficio);
            proveedor.setDescripcion(descripcion);
            proveedor.setTarifaPorHora(tarifaPorHora);
            proveedor.setEstado(true);
            proveedor.setRol(Rol.PROVEEDOR);
            proveedor.setFecha_alta(new Date());

            String idImagen = null;

            if (proveedor.getImagen() != null) {
                idImagen = proveedor.getImagen().getId();
            }
            Imagen img = imagenServicio.actualizar(imagen, idImagen);

            proveedor.setImagen(img);

            proveedorRepositorio.save(proveedor);
        }
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

    public void cambiarestado(String id) {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setEstado(!proveedor.getEstado());

            proveedorRepositorio.save(proveedor);
        }

    }

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Proveedor proveedor = proveedorRepositorio.buscarPorEmail(email);

        if (proveedor != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("proveedorsession", proveedor);

            return new User(proveedor.getEmail(), proveedor.getPassword(), permisos);

        } else {
            return null;
        }
    }
}


