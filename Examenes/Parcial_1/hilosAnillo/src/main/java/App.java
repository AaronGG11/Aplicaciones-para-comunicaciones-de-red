
import Logica.Hilo;
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
    public static void main(String[] args) 
    {
        int numero_hilos = 10;
        int hilo_inicializador = 5;
        
        Hashtable<Integer, Hilo> threadPool = new Hashtable<>();

        
        for(int i=1; i<=numero_hilos; i++){
            threadPool.put(i, new Hilo(numero_hilos, i));
        }
        
        
        
        
        
        

    }
}
