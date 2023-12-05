package com.proyectoFinal.demo.entidades;
import javax.persistence.*;

import com.proyectoFinal.demo.enumeraciones.Rol;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
@Entity
public class Usuario {
    
@Id
@GeneratedValue(generator = "uuid")
@GenericGenerator(name = "uuid", strategy = "uuid2" )
private String id;
private String nombre;
private String apellido;
private String email;
private String password;
private String DNI;
private String telefono;
private String direccion;
@Enumerated(EnumType.STRING)
private Rol rol;
@OneToOne
private Imagen archivo;
private Boolean estado;
@Temporal(TemporalType.DATE)
private Date fechaalta;

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Imagen getImagen() {
        return archivo;
    }

    public void setImagen(Imagen imagen) {
        this.archivo = imagen;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(Date fechaalta) {
        this.fechaalta = fechaalta;
    }


}
