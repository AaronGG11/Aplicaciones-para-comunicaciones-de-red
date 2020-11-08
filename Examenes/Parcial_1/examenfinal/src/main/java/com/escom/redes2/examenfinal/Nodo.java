/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escom.redes2.examenfinal;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aarongarcia
 */
public class Nodo{
    private Integer nodo_anterior;
    private Integer nodo_siguiente;
    private Integer nodo_actual;
    
    private Integer status;
    // 0 -> initial
    // 1 -> no encontrado -> wait
    // 2 -> encontrado
    
    private String nombre_archivo;
    private String path_carpeta;
    private String path_archivo;

    public Nodo(Integer hilo_actual) {
        this.nodo_actual = hilo_actual;
        this.status = 0;
        
        this.path_carpeta = System.getProperty("user.dir").replace("examenfinal", 
                "archivos" + 
                File.separator + 
                "carpeta_" +
                hilo_actual +
                File.separator);
        
        this.path_archivo = null;
        this.nombre_archivo = null;
    }
    
    public synchronized void buscarArchivo(String nombre_archivo){
        File f = new File(path_carpeta);
        
        if (f.exists()){ 
            File[] ficheros = f.listFiles();
            
            for (int x=0;x<ficheros.length;x++){
                if(this.nombre_archivo.equals(ficheros[x].getName())){
                    path_archivo = path_carpeta + this.nombre_archivo;
                }
            }  
        }
        
        if(path_archivo.isEmpty() || path_archivo == null){
            status = 1;
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Nodo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            status = 2;
        }
    }
    
    
    public synchronized void esperar(){
    
    }

    public Integer getNodo_anterior() {
        return nodo_anterior;
    }

    public void setNodo_anterior(Integer nodo_anterior) {
        this.nodo_anterior = nodo_anterior;
    }

    public Integer getNodo_siguiente() {
        return nodo_siguiente;
    }

    public void setNodo_siguiente(Integer nodo_siguiente) {
        this.nodo_siguiente = nodo_siguiente;
    }

    public Integer getNodo_actual() {
        return nodo_actual;
    }

    public void setNodo_actual(Integer nodo_actual) {
        this.nodo_actual = nodo_actual;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getPath_carpeta() {
        return path_carpeta;
    }

    public void setPath_carpeta(String path_carpeta) {
        this.path_carpeta = path_carpeta;
    }

    public String getPath_archivo() {
        return path_archivo;
    }

    public void setPath_archivo(String path_archivo) {
        this.path_archivo = path_archivo;
    }
    
    
}
