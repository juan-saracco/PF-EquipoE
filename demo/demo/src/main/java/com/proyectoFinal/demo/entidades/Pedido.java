
package com.proyectoFinal.demo.entidades;

import java.util.Date;
<<<<<<< HEAD
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Pedido {

=======
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

public class Pedido {
    
>>>>>>> 4447d73c3efda2822b320299b34532dd992cbfa6
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

<<<<<<< HEAD
    @OneToOne
    private Usuario consumidor;
    @OneToOne
    private Proveedor proveedor;
    private String solicitud;
    private Boolean estadoPedido;
    private Double cotizacion;

    @OneToOne
    private FeedBack feedBack;
    @Temporal(TemporalType.DATE)
    private Date alta;

    @Temporal(TemporalType.DATE)
    private Date fecha_modificacion;

=======
    private Consumidor consumidor;
  
    private Proveedor proveedor;    
  
    private String solicitud;
    
    private boolean estadoPedido;
  
    private Integer cotizacion;
    
    private FeedBack feedBack;
    
    @Temporal(TemporalType.DATE)
    private Date alta;

>>>>>>> 4447d73c3efda2822b320299b34532dd992cbfa6
    public Pedido() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

<<<<<<< HEAD
    public Usuario getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Usuario consumidor) {
=======
    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
>>>>>>> 4447d73c3efda2822b320299b34532dd992cbfa6
        this.consumidor = consumidor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

<<<<<<< HEAD
    public Boolean getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(Boolean estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Double getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Double cotizacion) {
        this.cotizacion = cotizacion;
    }

    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }

=======
    public boolean isEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(boolean estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Integer getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Integer cotizacion) {
        this.cotizacion = cotizacion;
    }

>>>>>>> 4447d73c3efda2822b320299b34532dd992cbfa6
    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

<<<<<<< HEAD
    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }
=======
    public FeedBack getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(FeedBack feedBack) {
        this.feedBack = feedBack;
    }
    
>>>>>>> 4447d73c3efda2822b320299b34532dd992cbfa6
}
