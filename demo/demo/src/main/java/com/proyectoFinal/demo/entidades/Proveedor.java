package com.proyectoFinal.demo.entidades;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Proveedor extends Usuario{

    @ManyToOne
    private Oficio oficio;
    private String descripcion;
    private Double tarifaPorHora;
    private Double calificacion;


    public Proveedor() {
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

    public Double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(Double tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
}
