package Modelo;

import java.net.*;
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Aaron Garcia
 */


public class Servidor {
    private static final int PUERTO = 9911;

    public static void main(String[] args) {
        try{
            // Creamos el socket
            ServerSocket s = new ServerSocket(7000);
            // Iniciamos el ciclo infinito del servidor
            for(;;){
                // Esperamos una conexión
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];
                String nombre = dis.readUTF();
                System.out.println("Recibimos el archivo:"+nombre);
                long tam = dis.readLong();
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                long recibidos=0;
                int n, porcentaje;
                while(recibidos < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    recibidos = recibidos + n;
                    porcentaje = (int)(recibidos*100/tam);
                    System.out.print("Recibido: "+porcentaje+"%\r");
                }//While
                System.out.print("\n\nArchivo recibido.\n");
                dos.close();
                dis.close();
                cl.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }//catch

    }
}
