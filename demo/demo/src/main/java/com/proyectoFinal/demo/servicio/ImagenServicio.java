package com.proyectoFinal.demo.servicio;

import com.proyectoFinal.demo.entidades.Imagen;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.repositorios.ImagenRepositorio;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen buscarimagen(String idImagen) throws MiException {
        Imagen img = new Imagen();
        if (idImagen != null) {
            Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);

            if (respuesta.isPresent()) {
                img = respuesta.get();
            }
        }
        return img;
    }

    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MiException {

        Imagen foto = new Imagen();
        try {
            if (archivo.isEmpty() || archivo == null) {
                byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/static/imagenes/user2.jpg"));
                foto.setMime("image/jpeg");
                foto.setNombre("user2.jpg");
                foto.setContenido(bytes);

            } else {
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getOriginalFilename());
                foto.setContenido(archivo.getBytes());
            }

            foto = imagenRepositorio.save(foto);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return foto;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {
        if (archivo != null) {
            try {
                Imagen foto = new Imagen();

                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);

                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }

                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return imagenRepositorio.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public void eliminarImagen(MultipartFile archivo, String id, String nombre, String apellido, String DNI, String email, String direccion, String telefono, String password, String password2) throws MiException {

        Optional<Imagen> respuesta = imagenRepositorio.findById(id);

        if (respuesta.isPresent()) {

            imagenRepositorio.deleteById(id);
        }

        if (!respuesta.isPresent()) {

            throw new MiException("Imagen no encontrada por Id" + id);

        }
    }

}

