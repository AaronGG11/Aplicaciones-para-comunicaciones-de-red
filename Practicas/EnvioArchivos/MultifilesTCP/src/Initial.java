import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Aaron Garcia
 */

public class Initial {
    public static void main(String[] args) {
        // Configuration values
        Integer longitud_buffer = 1024;
        Integer numero_archivos = 0;
        Boolean algoritmo_nigle = Boolean.TRUE;
        File archivos[] = null;

        // This is just a server
        Integer PORT = 7000;

        // JFile Chooser
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jf.setMultiSelectionEnabled(true);

        if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            archivos = jf.getSelectedFiles();
            numero_archivos = archivos.length;
        }
        System.out.println("Cargando archivos ...");

        try
        {
            ServerSocket server = new ServerSocket(PORT);
            server.setReuseAddress(true);
            System.out.println("Servicio iniciado ...");

            while(Boolean.TRUE){
                System.out.println("Esperando conexion ...");

                Socket client = server.accept();
                System.out.println("Cliente conectado desde: " + client.getInetAddress() + ":" + client.getPort());

                DataOutputStream dos = new DataOutputStream(client.getOutputStream()); // misma salida

                // Enviar longitud de buffer
                dos.writeInt(longitud_buffer);
                dos.flush();

                // Enviar numero de archivos a enviar
                dos.writeInt(numero_archivos);
                dos.flush();

                // Enviar bandera de algoritmo de nigle
                dos.writeBoolean(algoritmo_nigle);
                dos.flush();

                // Enviar cada archivo
                for(File archivo : archivos){
                    // Enviar nombre de archivo
                    String nombre_archivo = archivo.getName();
                    dos.writeUTF(nombre_archivo);
                    dos.flush();

                    // Enviar tamaño de archivo
                    Long longitud_archivo = archivo.length();
                    dos.writeLong(longitud_archivo);
                    dos.flush();

                    // Entrada de datos
                    String path_carpeta = archivo.getAbsolutePath();
                    DataInputStream dis = new DataInputStream(new FileInputStream(path_carpeta)); // diferente entrada

                    // Enviar archivo
                    byte[] b = new byte[1024];
                    long enviados = 0;
                    int porcentaje, n;

                    while (enviados < longitud_archivo){
                        n = dis.read(b);
                        dos.write(b,0,n);
                        dos.flush();
                        enviados = enviados+n;
                        porcentaje = (int)((enviados*100) / longitud_archivo);
                        System.out.print("Enviado: " + porcentaje + "%\r");
                    }

                    dis.close();
                    System.out.println("Se envio archivo: " + nombre_archivo);
                    Thread.sleep(200);
                }

                dos.close();
                client.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}