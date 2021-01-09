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

        // This is just a server
        Integer PORT = 7000;


        // Read files
        String archivo = new String();
        String nombre = new String();
        Long tam = 0L;


        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jf.setMultiSelectionEnabled(true);

        int r = jf.showOpenDialog(null);
        if (r==JFileChooser.APPROVE_OPTION) {
            File f = jf.getSelectedFile();  //Manejador
            archivo = f.getAbsolutePath(); //Dirección
            nombre = f.getName(); //Nombre
            tam = f.length();  //Tamaño

        }
        System.out.println("Cargando archivo ...");

        try
        {
            ServerSocket server = new ServerSocket(PORT);
            server.setReuseAddress(true);
            System.out.println("Servicio iniciado ...");

            while(Boolean.TRUE){
                System.out.println("Esperando conexion ...");

                Socket client = server.accept();
                System.out.println("Cliente conectado desde: " + client.getInetAddress() + ":" + client.getPort());

                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(archivo));

                dos.writeInt(longitud_buffer);
                dos.flush();

                dos.writeInt(numero_archivos);
                dos.flush();

                dos.writeBoolean(algoritmo_nigle);
                dos.flush();

                dos.writeUTF(nombre);
                dos.flush();

                dos.writeLong(tam);
                dos.flush();

                byte[] b = new byte[longitud_buffer];
                long enviados = 0;
                int porcentaje, n;

                while (enviados < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    enviados = enviados+n;
                    porcentaje = (int)(enviados*100/tam);
                    System.out.print("Enviado: "+porcentaje+"%\r");
                }//While
                System.out.println("Archivo enviado\n\n");
                dos.close();
                dis.close();

                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}