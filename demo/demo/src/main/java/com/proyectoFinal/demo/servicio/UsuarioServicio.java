package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Imagen;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.enumeraciones.Rol;
import com.proyectoFinal.demo.excepciones.MiException;
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
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombre, String apellido, String email, String password, String password2, String DNI, String telefono, String direccion, MultipartFile archivo)
            throws MiException {

        validar(nombre, apellido, email, password, password2, DNI, telefono, direccion);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setDNI(DNI);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setRol(Rol.USER);
        usuario.setEstado(true);
        usuario.setFecha_alta(new Date());

        Imagen foto = imagenServicio.guardar(archivo);

        usuario.setImagen(foto);

        usuarioRepositorio.save(usuario);
    }

    public void registrorapido(String nombre, String apellido, String email, String password)
            throws MiException {

        validarrapido(nombre, apellido, email, password);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        usuario.setEstado(true);
        usuario.setFecha_alta(new Date());

        usuarioRepositorio.save(usuario);
      //  return usuario;
    }


    @Transactional
    public void actualizar(String id, String nombre, String apellido, String email, String password, String password2, String DNI, String telefono, String direccion, MultipartFile imagen)
            throws MiException {

        validar(nombre, apellido, email, password, password2, DNI, telefono, direccion);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setDNI(DNI);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);
            usuario.setRol(Rol.USER);

            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            Imagen foto = imagenServicio.actualizar(imagen, idImagen);

            usuario.setImagen(foto);

            usuarioRepositorio.save(usuario);
        }
    }

    public List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }

    public void cambiarestado(String id) throws MiException {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
           Usuario U = respuesta.get();
           U.setEstado(!U.getEstado());
        }

        if (!respuesta.isPresent()) {
            throw new MiException("Usuario no encontrado por Id" + id);
        }

    }

    private void validar(String nombre, String apellido, String email, String password, String password2, String DNI, String telefono, String direccion) throws MiException {

      /*Usuario usuarioDNI = usuarioRepositorio.buscarPorDNI(DNI);
      Usuario usuarioEmail = usuarioRepositorio.buscarPorEmail(email);*/


        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El apellido no puede ser nulo o estar vacio");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacio");
        }
      /*  if(email.equals(usuarioEmail.getEmail())){
            throw new MiException("El usuario ya existe. Intente nuevamente");
        }*/

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede ser nulo o estar vacia y no puede tener menos de 5 digitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas no pueden ser diferentes");

        }

        if (DNI.isEmpty() || DNI == null) {
            throw new MiException("El DNI no puede ser nulo o estar vacio");
        }
        /*if(DNI.equals(usuarioDNI.getDNI())){
            throw new MiException("El usuario ya existe. Intente nuevamente");
        }*/

        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }

        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("La direccion no puede ser nulo o estar vacio");
        }

    }

    private void validarrapido(String nombre, String apellido, String email, String password) throws MiException {


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

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        } else {
            return null;
        }
    }

}
