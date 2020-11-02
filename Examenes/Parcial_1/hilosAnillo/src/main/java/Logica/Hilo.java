package Logica;

import java.io.File;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author aarongarcia
 */

@Getter
@Setter
@Builder


public class Hilo extends Thread{
    private Integer numero_hilos;
    private Integer actual;
    private Integer anterior;
    private Integer siguiente;
    
    private Boolean esperando;
    private String path_carpeta;
    private String path_archivo;
    private String archivo_buscar;

    public Hilo(Integer numero_hilos, Integer actual) {
        this.numero_hilos = numero_hilos;
        this.actual = actual;
        this.esperando = false;
        
        this.path_carpeta = System.getProperty("user.dir").replace("hilosAnillo", 
                "archivos" + 
                File.separator + 
                "carpeta_" +
                actual +
                File.separator);
        
        if(actual == numero_hilos){
            this.anterior = actual - 1;
            this.siguiente = 1;
        }else if(actual == 1){
            this.anterior = numero_hilos;
            this.siguiente = actual + 1;
        }else{
            this.anterior = actual - 1;
            this.siguiente = actual + 1;
        }
    }
    
    
    
    public void preguntarSiguiente(Hilo siguiente, String archivo_buscar){
        
    }

    @Override
    public String toString() {
        return "Hilo{" + "actual=" + actual + ", anterior=" + anterior + ", siguiente=" + siguiente + '}';
    }
    
    
    @Override
    public void run(){
        System.out.println(this.toString());
    }

}
