package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Imagen;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.enumeraciones.Rol;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
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
public class UsuarioServicio implements UserDetailsService  {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String id, String nombre, String apellido, String DNI, String email, String direccion, String telefono, String password, String password2) throws MiException {
        validar(nombre, apellido, DNI, email, direccion, telefono, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDNI(DNI);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);           // << Fijarse si no genera algun error, en el template tiene 1 nombre y en el portalcontrolador se guia por el template.

        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setRol(Rol.USER);

        Imagen foto = imagenServicio.guardar(archivo);

        usuario.setFoto(foto);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String id, String nombre, String apellido, String DNI, String email, String direccion, String telefono, String password, String password2) throws MiException {

        validar(nombre, apellido, DNI, email, direccion, telefono, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDNI(DNI);
            usuario.setDireccion(direccion);
            usuario.setTelefono(telefono);
            usuario.setEmail(email);

            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuario.setRol(Rol.USER);

            String idImagen = null;

            if (usuario.getFoto() != null) {
                idImagen = usuario.getFoto().getId();
            }
            Imagen foto = imagenServicio.actualizar(archivo, idImagen);

            usuario.setFoto(foto);

            usuarioRepositorio.save(usuario);
        }

    }

    public void eliminarUsuario(String id) throws MiException {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
        }
        usuarioRepositorio.deleteById(id);

        if (!respuesta.isPresent()) {

            throw new MiException("Usuario no encontrado por Id" + id);

        }
    }

    private void validar(String nombre, String apellido, String DNI, String email, String direccion, String telefono, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }

        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("el apellido no puede ser nulo o estar vacio");
        }

        if (DNI.isEmpty() || DNI == null) {
            throw new MiException("el DNI no puede ser nulo o estar vacio");
        }

        if (direccion.isEmpty() || direccion == null) {
            throw new MiException("la direccion no puede ser nulo o estar vacio");
        }

        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("el telefono no puede ser nulo o estar vacio");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("el password no puede ser nulo o estar vacio");
        }

        if (!password.equals(password2)) {
            throw new MiException("las contraseñas no puede estar vacio y debe tener mas de 5 digitos");

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

    public void eliminarUsuario(MultipartFile archivo, String id, String nombre, String apellido, String DNI, String email, String direccion, String telefono, String password, String password2) throws MiException {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            usuarioRepositorio.deleteById(id);
        }

        if (!respuesta.isPresent()) {

            throw new MiException("Usuario no encontrado por Id" + id);

        }
    }
}

