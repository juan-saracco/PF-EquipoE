package com.proyectoFinal.demo.entidades;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class FeedBack {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2" )
    private String id;
    private Integer calificacion;
    private String comentario;
    @Temporal(TemporalType.DATE)
    private Date alta;
    private Boolean estado;
    @OneToOne
    private Pedido pedido;
    public FeedBack() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}