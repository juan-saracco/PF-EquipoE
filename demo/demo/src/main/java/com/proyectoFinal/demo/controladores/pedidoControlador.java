/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectoFinal.demo.controladores;

import com.proyectoFinal.demo.entidades.Oficio;
import com.proyectoFinal.demo.entidades.Pedido;
import com.proyectoFinal.demo.servicio.PedidoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author LENOVO
 */
 
@Controller
@RequestMapping("/pedido")
public class pedidoControlador {
    @Autowired
    PedidoServicio pedidoServicio;
    
    @GetMapping("/listaTodos") //MUESTRA TODOS LOS PEDIDOS, TANTO ACTIVOS COMO DADOS DE BAJA ->SIRVE PARA MOSTRAR LOS PEDIDOS AL ADMINISTRADOR
    public String listarTodos(ModelMap modelo, @ModelAttribute("exi") String ex) {
        List<Pedido> pedidos = pedidoServicio.listarPedidos();
        modelo.addAttribute("pedidos", pedidos);

        if (ex != null) {
            modelo.put("exi", ex);
        }
        return "listaPedidos.html";
    }
    
    @GetMapping
    public String mostrarFormulario(Model model){
        return "pedidoUsuario";
    }
    
    @PostMapping("/enviar-pedido")
    public String enviarFormulario(Model model, String nombre, String email, String celular, String pedido){
        
        return "";
    }
    
}
    

