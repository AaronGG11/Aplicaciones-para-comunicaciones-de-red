package Modelo;

import java.net.*;
import java.io.*;
import javax.swing.JFileChooser;

public class Cliente {
    private static final int PUERTO = 9911;
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PUERTO);
        s.setReuseAddress(true);
        System.out.println("Servicio iniciado...");
        for (; ; ) {
            System.out.println("Eperando conexion...");
            Socket cl = s.accept();
            System.out.format("Cliente conectado desde: %s:%s\n", cl.getInetAddress(), cl.getPort());
            DataInputStream dis = new DataInputStream(cl.getInputStream());
            String ruta = dis.readUTF();
            String nombre = dis.readUTF();

            escribirArchivo("", dis);

            System.out.println("¡Archivo recibido!\n");
            dis.close();
            cl.close();
        }

    }

    private static void escribirArchivo(String nombre, DataInputStream dis) {
        System.out.format("Escribiendo el archivo: %s\n", nombre);
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
            long recibidos = 0;
            long tam = dis.readLong();
            int n;
            while (recibidos < tam) {
                byte[] buffer = new byte[1500];
                n = dis.read(buffer);
                dos.write(buffer, 0, n);
                dos.flush();
                recibidos += n;
            }
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


