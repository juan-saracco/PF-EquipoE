package com.proyectoFinal.demo.servicio;

<<<<<<< HEAD
import com.proyectoFinal.demo.entidades.Oficios;
=======
import com.proyectoFinal.demo.entidades.Imagen;
>>>>>>> desarrollo
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.entidades.Usuario;
import com.proyectoFinal.demo.enumeraciones.Rol;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.ProveedorRepositorio;
import com.proyectoFinal.demo.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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

/**
 *
 * @author ILIANA
 */
@Service
public class ProveedorServicio extends Usuario  implements UserDetailsService  {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

<<<<<<< HEAD
    public ProveedorServicio() {
        super();
    }

    @Transactional
    public void registrar(MultipartFile archivo, String id, String nombre, String apellido, Oficios oficio, String descripcion, Integer tarifaPorHora, String telefono, Double calificacion) throws MiException {

        validar(oficio, descripcion, tarifaPorHora, calificacion);

        Usuario usuario = usuarioRepositorio.findById(id).get();

       Proveedor proveedor = new Proveedor();
=======
    @Transactional
    public void crearUsuario(MultipartFile archivo, String id, String nombre,
            String apellido, String DNI, String email, Enum oficio, String descripcion,
            Integer tarifaPorHora, String telefono, String direccion, String password, String password2) throws MiException {

        validar(nombre, apellido, DNI, email, oficio, descripcion, tarifaPorHora, direccion, telefono, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDNI(DNI);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);

        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setRol(Rol.USER);

        Imagen foto = imagenServicio.guardar(archivo);

        usuario.setFoto(foto);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String id, String nombre,
            String apellido, String DNI, String email, Enum oficio, String descripcion,
            Integer tarifaPorHora, String telefono, String direccion, String password, String password2) throws MiException {

        validar(nombre, apellido, DNI, email, oficio, descripcion, tarifaPorHora, direccion, telefono, password, password2);
>>>>>>> desarrollo

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
          Proveedor proveedor = new Proveedor();
          
        if (respuesta.isPresent()) {
            proveedor  = respuesta.get();
       
        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setDNI(DNI);
        proveedor.setTelefono(telefono);
        proveedor.setDireccion(direccion);
        proveedor.setEmail(email);
        proveedor.setOficio(oficio);
        proveedor.setDescripcion(descripcion);
        proveedor.setTarifaPorHora(tarifaPorHora);

        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));

        proveedor.setRol(Rol.USER);
        
          String idImagen = null;

            if (proveedor.getFoto() != null) {
                idImagen = proveedor.getFoto().getId();
            }
            Imagen foto = imagenServicio.actualizar(archivo, idImagen);

            proveedor.setFoto(foto);


        proveedorRepositorio.save(proveedor);
<<<<<<< HEAD

    }

     public List<Proveedor> listarProveedores() {
=======
    }
    }

    public List<Proveedor> listarProveedores() {
>>>>>>> desarrollo
        List<Proveedor> proveedores = new ArrayList();

        proveedores = (proveedorRepositorio.findAll());

        return proveedores;
    }

    private void validar(String nombre, String apellido, String DNI, String email, Enum oficio, String descripcion, Integer tarifaPorHora, String direccion, String telefono, String password, String password2) throws MiException {

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

        /* if (oficio.name()|| oficio == null) {
            throw new MiException("el oficio no puede ser nulo o estar vacio");
         }*/
        if (descripcion.isEmpty() || descripcion == null) {
            throw new MiException("la descripcion no puede ser nula o estar vacio");
        }
        if (tarifaPorHora.intValue() <=0 || tarifaPorHora == null) {
            throw new MiException("la tarifa no puede ser nula o estar vacio");
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

    public void eliminarProveedor(MultipartFile archivo, String id, String nombre,
            String apellido, String DNI, String email, Enum oficio, String descripcion,
            Integer tarifaPorHora, String telefono, String direccion, String password, String password2) throws MiException {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            proveedorRepositorio.deleteById(id);
        }

        if (!respuesta.isPresent()) {

            throw new MiException("Usuario no encontrado por Id" + id);

        }

    }
}
<<<<<<< HEAD

}

=======
>>>>>>> desarrollo
