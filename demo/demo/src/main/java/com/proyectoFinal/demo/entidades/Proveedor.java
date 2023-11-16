package com.proyectoFinal.demo.entidades;

import javax.persistence.Entity;

/**
 *
 * @author maresca
 */
@Entity
public class Proveedor extends Usuario{
    
    private Oficio oficio;
    
    private String descripcion;
     
    private Integer tarifaPorHora;
    
    private Double calificacion;

    public Proveedor() {
        super();
    }

    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
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
