/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aarongarcia
 */
public class Hilo extends Thread{
    private Hilo anterior;
    private Hilo siguiente;
    
    private Integer bandera;
    private Integer actual;
    
    private String nombre_archivo;
    private String path_carpeta;
    private String path_archivo;

    // 1 -> free
    // 2 -> search
    // 3 -> find
    // 4 -> wait

    public Hilo(Integer actual) {
        this.actual = actual;
        this.bandera = 1; // libre
        
        this.path_carpeta = System.getProperty("user.dir").replace("hilosAnillo", 
                "archivos" + 
                File.separator + 
                "carpeta_" +
                actual +
                File.separator);
        
        this.path_archivo = null;
        this.nombre_archivo = null;
    }
    
    public void Buscar(){
        File f = new File(this.getPath_carpeta());
        
        if (f.exists()){ 
            File[] ficheros = f.listFiles();
            for (int x=0;x<ficheros.length;x++){
                if(this.getNombre_archivo().equals(ficheros[x].getName())){
                    this.setPath_archivo(this.getPath_carpeta() + this.getNombre_archivo());
                }
            }      
        }
    }

    
    @Override
    public void run() { 
        synchronized(this){
            while(true){
                System.out.println("Hilo: " + getActual());
                if(getBandera() == 1){// free
                        setBandera(2);
                    System.out.println("1");
                }else if(getBandera() == 2){//search
                        Buscar();

                        if(!( getPath_archivo()==null )){// encontrado
                            setBandera(3);
                        }else{// No encontrado
                            if(getSiguiente().bandera == 1){// siguiente libre
                                setBandera(4); // voy a espear a que siguiente lo encuentre  
                            }else{// siguiente no libre -> ya di la vuelta
                                setBandera(3);// ya termine, encontro null archivo  
                            }
                        }
                        System.out.println("2");

                }else if(getBandera() == 3){// encontrado
                        if(getAnterior().getBandera() == 4){ // retroceder a hilo anterior
                            getAnterior().setPath_archivo(getPath_archivo());
                            getAnterior().notify();
                        }else{// ya regrese al nodo inicial
                            System.out.println("Regrese al inicio " + getActual() + ", path: " + getPath_archivo()); 
                        }
                    System.out.println("3");
                }else{//esperando, buscar en siguiente hilo
                    getSiguiente().setNombre_archivo(nombre_archivo);
                    getSiguiente().start();

                    System.out.println("4");
                        try {
                            this.wait();
                            setBandera(4);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
                        }

                }
            }
        }
    }
    
    
    
    
    
    

    public Hilo getAnterior() {
        return anterior;
    }

    public void setAnterior(Hilo anterior) {
        this.anterior = anterior;
    }

    public Hilo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Hilo siguiente) {
        this.siguiente = siguiente;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
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
