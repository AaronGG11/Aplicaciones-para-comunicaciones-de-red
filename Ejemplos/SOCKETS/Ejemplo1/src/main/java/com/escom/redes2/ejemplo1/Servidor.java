/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escom.redes2.ejemplo1;
import java.io.*;
import java.net.*;

/**
 *
 * @author aarongarcia
 */
public class Servidor {
    public static void main(String[] args)
    {
        try{
            // Se crea el socket
            ServerSocket s = new ServerSocket(1234);
            System.out.println("Esperando cliente ...");
            // Iniciamos el ciclo infinito 
            for(;;){
                // Tenemos un bloqueo, en el momento que llegue una conexión continua el programa
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde "+  cl.getInetAddress()+":" + cl.getPort());
                String mensaje ="Hola mundo";
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                // Se envía el mensaje
                pw.println(mensaje);
                // Se limpia le flujo
                pw.flush();
                pw.close();
                cl.close();
            }//for
        }catch(Exception e){ // Manejo de excepciones
            e.printStackTrace();
        }//catch
    }
}
