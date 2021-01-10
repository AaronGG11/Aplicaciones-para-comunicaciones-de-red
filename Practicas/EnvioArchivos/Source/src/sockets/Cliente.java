package sockets;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        // This is just a client
        StringBuilder LOCAL_PATH = new StringBuilder();
        LOCAL_PATH.append(".");
        LOCAL_PATH.append(File.separator);
        LOCAL_PATH.append("terminal_files");
        LOCAL_PATH.append(File.separator);

        // Server's credentials to connect
        String HOST = "localhost";
        Integer PORT = 9999;

        try {
            Socket cliente = new Socket(HOST, PORT);
            System.out.println("Cliente: " + cliente.getLocalSocketAddress() + " conectado");

            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            DataInputStream dis = new DataInputStream(cliente.getInputStream());

            dos.writeInt(2);
            dos.flush();

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
                int n;
                while (recibidos < longitud_archivo) {
                    byte[] buffer = new byte[1500];
                    n = dis.read(buffer);
                    dos_archivos.write(buffer, 0, n);
                    dos_archivos.flush();
                    recibidos += n;
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
