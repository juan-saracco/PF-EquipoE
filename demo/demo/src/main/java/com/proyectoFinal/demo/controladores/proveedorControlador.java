package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.entidades.Proveedor;
import com.proyectoFinal.demo.excepciones.MiException;
import com.proyectoFinal.demo.servicio.OficioServicio;
import com.proyectoFinal.demo.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proveedor")
public class proveedorControlador {
    @Autowired
    public ProveedorServicio proveedorservicio;
    @Autowired
    public OficioServicio oficioservicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Oficio> oficios = oficioservicio.listarTodosOficios();

        modelo.addAttribute("oficios", oficios);
        return "registroProveedor.html";
    }

    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam String nombre, 
            @RequestParam String apellido, @RequestParam String DNI, @RequestParam String email, 
            @RequestParam String direccion, @RequestParam String telefono, @RequestParam String password, 
            @RequestParam String password2, @RequestParam String oficio, @RequestParam String descripcion, 
            @RequestParam(required=false) Double tarifaPorHora, RedirectAttributes redi, ModelMap ModeloProveedor,
            @ModelAttribute("Err") String err) {
        
        try {
            proveedorservicio.registrar(nombre, apellido, email, password, password2, DNI, telefono, direccion, archivo, oficio, descripcion, tarifaPorHora);
            redi.addFlashAttribute("Exito", "Usuario registrado correctamente.");
            return "redirect:/";

        } catch (MiException e) {
            List<Oficio> oficios = oficioservicio.listarTodosOficios();
            ModeloProveedor.addAttribute("oficios", oficios);
            
            ModeloProveedor.put("Error", e.getMessage());
            ModeloProveedor.put("nombre", nombre);
            ModeloProveedor.put("apellido", apellido);
            ModeloProveedor.put("email", email);
            ModeloProveedor.put("DNI", DNI);
            ModeloProveedor.put("telefono", telefono);
            ModeloProveedor.put("direccion", direccion);
            ModeloProveedor.put("archivo", archivo);
            ModeloProveedor.put("oficio", oficio);
            ModeloProveedor.put("descripcion", descripcion);
            ModeloProveedor.put("tarifaPorHora", tarifaPorHora);

            return "registroProveedor.html";
        }
    }

    @PostMapping("/cambiarestado/{id}") //->SIRVE PARA EL USUARIO, CUANDO QUIERE DAR DE BAJA SU PERFIL
    public String cambiarestadoPorId(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.addAttribute("Exito", "Se dio de baja el usuario correctamente");
            proveedorservicio.cambiarestado(id);
        } catch (Exception e) {
            modelo.put("Error", "Error: no se pudo borrar el usuario");
        }
        return "modificarUsuario.html";
    }
    
    @GetMapping("/desactReactProveedores/{id}")
    public String estadoProveedor(@PathVariable String id) {
        proveedorservicio.cambiarestado(id);

        return "redirect:/proveedor/listaTodos";
    }
    
     @GetMapping("/modificarOficioProveedor/{id}")
    public String modifOficioProveedor(@PathVariable String id) {

        return "editarOficioProveedor.html";
    }
    
    @GetMapping("/lista") //MUESTRA SOLO LOS PROVEEDORES ACTIVOS (ESTADO: TRUE) ->SIRVE PARA LISTAR LOS OFICIOS PARA LOS USUARIOS
    public String listar(ModelMap modelo, @ModelAttribute("exi") String ex) {
        List<Proveedor> proveedores = proveedorservicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listarProveedores.html";
    }

    @GetMapping("/listaTodos") //MUESTRA TODOS LOS OFICIOS, TANTO ACTIVOS COMO DADOS DE BAJA ->SIRVE PARA MOSTRAR LOS OFICIOS AL ADMINISTRADOR
    public String listarTodos(ModelMap modelo, @ModelAttribute("exi") String ex) {
        List<Proveedor> proveedores = proveedorservicio.listarTodosProveedores();
        modelo.addAttribute("proveedores", proveedores);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listarProveedores.html";
    }

}
