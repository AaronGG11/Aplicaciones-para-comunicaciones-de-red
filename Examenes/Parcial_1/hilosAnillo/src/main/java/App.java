
import Logica.Hilo;
import Vista.VentanaHilo;
import Vista.VentanaInicio;
import java.util.Hashtable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aarongarcia
 */

public class App {
    public static void main(String[] args) throws InterruptedException 
    {
        Integer numero_hilos = 10;
        Integer hilo_inicializador = 1;
        String archivo_buscar = "a4.txt";
        
        Hashtable<Integer,Hilo> threadPool = new Hashtable<>();
        
        // Creando hilos 
        for(Integer i=1; i<=numero_hilos; i++){
            threadPool.put(i, new Hilo(i));
        }
        
        // Asignando anterior y siguiente a cada hilo
        for(Integer i=1; i<=numero_hilos; i++){
            if(i==1){
                threadPool.get(i).setAnterior(threadPool.get(numero_hilos));
                threadPool.get(i).setSiguiente(threadPool.get(i+1));
            }else if(i==numero_hilos){
                threadPool.get(i).setAnterior(threadPool.get(numero_hilos-1));
                threadPool.get(i).setSiguiente(threadPool.get(1));
            }else{
                threadPool.get(i).setAnterior(threadPool.get(i-1));
                threadPool.get(i).setSiguiente(threadPool.get(i+1));
            }
        }
        
        threadPool.get(hilo_inicializador).setNombre_archivo(archivo_buscar);
        threadPool.get(hilo_inicializador).start();
        
        
    }
}
