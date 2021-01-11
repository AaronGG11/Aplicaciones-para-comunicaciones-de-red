package sockets;

import java.io.*;
import java.net.Socket;

/**
 * @author Aaron Garcia
 */
public class SocketEnvio {
    private final String host;
    private final int port;
    private final int tam_buffer;
    private final boolean algoritmo_nigle;


    public SocketEnvio(String host, int port, int tam_buffer, boolean algoritmo_nigle) {
        this.host = host;
        this.port = port;
        this.tam_buffer = tam_buffer;
        this.algoritmo_nigle = algoritmo_nigle;
    }

    public void enviarArchivo(File file, String destino) throws IOException {
        Socket cl = new Socket(host, port);
        String nombre = file.getName();

        cl.setTcpNoDelay(algoritmo_nigle);

        long tam = file.length();
        long enviados = 0;
        int porcentaje;
        int n;
        String ruta = file.getAbsolutePath();

        System.out.println("Conexion establecida");
        DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
        DataInputStream dis = new DataInputStream(new FileInputStream(ruta));

        // tipo de cliente
        dos.writeInt(1); // cliente tipo 1
        dos.flush();

        // tam de buffer
        dos.writeInt(tam_buffer);
        dos.flush();

        // bandera de niggle
        dos.writeBoolean(algoritmo_nigle);
        dos.flush();

        // Usamos destino para ir almacenando la ruta del archivo/carpeta
        dos.writeUTF(destino);
        dos.flush();
        // Despues mandamos el nombre del archivo
        dos.writeUTF(nombre);
        dos.flush();
        // Y finalmente su tamaño
        dos.writeLong(tam);
        dos.flush();

        System.out.format("Enviando el archivo: %s...\n", nombre);
        System.out.format("Que esta en la ruta: %s\n", ruta);

        while (enviados < tam) {
            byte[] b = new byte[tam_buffer];
            n = dis.read(b);
            dos.write(b, 0, n);
            dos.flush();
            enviados = enviados + n;
            porcentaje = (int) (enviados * 100 / tam);
            System.out.print("\rSe ha transmitido el: " + porcentaje + "% ...\n");
        }

        System.out.println("Archivo enviado");
        cl.close();
        dos.close();
        dis.close();
    }

    // el parametro destino nos permite guardar la ubicacion del archivo
    public void enviarCarpetas(File carpeta, String destino) throws IOException {
        System.out.format("Carpeta %s con los archivos:\n", carpeta.getName());

        if (destino.equals("")) destino = carpeta.getName(); // evita que se cree en c:\\
        else destino = destino + "\\" + carpeta.getName(); // concatenar la ruta de los archivos
        for (File file : carpeta.listFiles()) {
            if (file.isDirectory()) enviarCarpetas(file, destino);
            else enviarArchivo(file, destino);
        }
    }

}
