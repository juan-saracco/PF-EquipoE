package com.proyectoFinal.demo.entidades;

import javax.persistence.Entity;

/**
 *
 * @author maresca
 */
@Entity
public class Proveedor extends Usuario{
    
<<<<<<< HEAD
<<<<<<< HEAD
    private Oficios oficio;
=======
    private Oficio oficio;
>>>>>>> desarrollo
    
    private String descripcion;
     
    private Integer tarifaPorHora;
    
    private Double calificacion;
=======
private Oficios oficio;
private String descripcion;
private Integer tarifaPorHora;
private Double calificacion;
>>>>>>> rjr2

    public Proveedor() {
    }

<<<<<<< HEAD
    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
=======
    public Oficios getOficio() {
        return oficio;
    }

    public void setOficio(Oficios oficio) {
>>>>>>> rjr2
        this.oficio = oficio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(Integer tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
}
