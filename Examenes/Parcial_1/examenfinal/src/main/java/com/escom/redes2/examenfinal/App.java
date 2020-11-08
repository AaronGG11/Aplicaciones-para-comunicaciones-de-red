/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escom.redes2.examenfinal;

import java.util.Hashtable;

/**
 *
 * @author aarongarcia
 */
public class App {
    public static void main(String[] args) {
        Integer hilo_iniciador = 5;
        Integer numero_hilos = 10;
        Hashtable<Integer, Nodo> threadPool = new Hashtable<>();
        
        String archivo = "a.txt";
        
        for(int i=1; i<=numero_hilos; i++){
            threadPool.put(i, new Nodo(i));
        }
        
        for(int i=1; i<=numero_hilos; i++){
            if(i==1){
                threadPool.get(i).setNodo_anterior(numero_hilos);
                threadPool.get(i).setNodo_siguiente(i+1);
            }else if(i==numero_hilos){
                threadPool.get(i).setNodo_anterior(1);
                threadPool.get(i).setNodo_siguiente(i-1);
            }else{
                threadPool.get(i).setNodo_anterior(i-1);
                threadPool.get(i).setNodo_siguiente(i+1);
            }
        }
        
        
        
    }
}
