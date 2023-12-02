package com.proyectoFinal.demo.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class errorControlador implements ErrorController{
    
    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest){
        
        ModelAndView errorPage = new ModelAndView("Error");
        
        String errorMsj = "";
        
        int httpErrorCode = getErrorCode(httpRequest);
        
        switch(httpErrorCode){
            
            case 4:{
                errorMsj = "Usuario o contraseña incorrectos";
                break;
            }     
            case 400:{
                errorMsj = "El recurso solicitado no existe";
                break;
            }            
            case 401:{
                errorMsj = "No se encuentra autorizado";
                break;
            }
            case 403:{
                errorMsj = "No tiene permisos para acceder al recurso";
                break;
            }
            case 404:{
                errorMsj = "El recurso solicitado no fue encontrado";
                break;
            }
            case 500:{
                errorMsj = "Ocurrió un error interno";
                break;
            }   
             
        }
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsj);
        
        return errorPage;
    }
    
    
    private int getErrorCode(HttpServletRequest httpRequest){
        
        return (int) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
    
    public String getErrorPath() { 
		return "/error.html";
	}
}


