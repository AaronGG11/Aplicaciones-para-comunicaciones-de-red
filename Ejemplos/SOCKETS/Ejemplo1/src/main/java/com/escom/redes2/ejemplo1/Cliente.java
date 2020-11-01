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
public class Cliente {
    public static void main(String[] args) 
    {
      try{
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Escriba la dirección del servidor: ");
            String host = br1.readLine();
            System.out.printf("\n\nEscriba el puerto:");
            int pto = Integer.parseInt(br1.readLine());
            // Creamos el socket y nos conectamos
            Socket cl = new Socket(host,pto);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            // Nos conectamos
            String mensaje = br2.readLine();
            System.out.println("Recibimos un mensaje desde el servidor");
            System.out.println("Mensaje:"+mensaje);
            // Cerramos flujos y socket
            br1.close();
            br2.close();
            cl.close();
        }catch(Exception e){ //Manejo de excepciones
            e.printStackTrace();
        }
    }
}
