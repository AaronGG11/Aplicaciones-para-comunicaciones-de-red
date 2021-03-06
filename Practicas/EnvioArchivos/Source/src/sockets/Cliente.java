package sockets;

import java.io.*;
import java.net.Socket;

/**
 * @author Aaron Garcia
 */
public class Cliente {
    public static void main(String[] args) {
        // This is just a client
        StringBuilder LOCAL_PATH = new StringBuilder();
        LOCAL_PATH.append(".");
        LOCAL_PATH.append(File.separator);
        LOCAL_PATH.append("terminal_files");
        LOCAL_PATH.append(File.separator);

        // Server's credentials to connect
        String HOST = "201.114.240.145";
        Integer PORT = 9999;

        try {
            Socket cliente = new Socket(HOST, PORT);
            System.out.println("Cliente: " + cliente.getLocalSocketAddress() + " conectado");

            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            DataInputStream dis = new DataInputStream(cliente.getInputStream());

            dos.writeInt(2);
            dos.flush();

            Integer tam_buffer = dis.readInt();
            Boolean algoritmo_nigle = dis.readBoolean();

            cliente.setTcpNoDelay(algoritmo_nigle);

            Integer numero_archivos = 0;
            numero_archivos= dis.readInt();

            Integer contador = 0;

            while(contador < numero_archivos){
                String nombre_Archivo = dis.readUTF();
                Long longitud_archivo = dis.readLong();

                System.out.println(nombre_Archivo + ":" + longitud_archivo);
                String ruta = LOCAL_PATH + nombre_Archivo;
                System.out.println(ruta);

                DataOutputStream dos_archivos = new DataOutputStream(new FileOutputStream(ruta));
                long recibidos = 0;
                int porcentaje;
                int n;
                while (recibidos < longitud_archivo) {
                    byte[] buffer = new byte[tam_buffer];
                    n = dis.read(buffer);
                    dos_archivos.write(buffer, 0, n);
                    dos_archivos.flush();
                    recibidos += n;
                    porcentaje = (int) (recibidos * 100 / longitud_archivo);
                    System.out.println("\rSe ha recibido el: " + porcentaje + "% ...");
                }

                dos_archivos.close();


                contador += 1;
            }


            dis.close();
            dos.close();
            cliente.close();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
