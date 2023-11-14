package com.proyectoFinal.demo.entidades;

import javax.persistence.Entity;

@Entity
public class Consumidor extends Usuario{
    
    private String pedido;

    public Consumidor() {
        super();
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }
    
}
