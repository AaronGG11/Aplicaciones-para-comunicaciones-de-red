package Modelo;

import javax.swing.*;
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Aaron Garcia
 */


public class Servidor extends javax.swing.JFrame {
    public static void main(String[] args) {
        // Obtener valores de configuracion
        Integer longitud_paquete = 1024;
        Boolean algoritmo_nigle = Boolean.TRUE;
        Integer numero_archivos = 0;
        StringBuilder nombres_archivos = new StringBuilder();
        File archivos[] = null;

        // Leer archivos a enviar
        // file chooser que acepta multiples archivos y carpetas
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jf.setMultiSelectionEnabled(true);
        int r = jf.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {
            archivos = jf.getSelectedFiles();
            for (File archivo : archivos) {
                nombres_archivos.append(archivo.getName());
                nombres_archivos.append(" -- ");
                nombres_archivos.append(archivo.length());
                nombres_archivos.append(" bytes\n");
            }
        }

        //JOptionPane.showMessageDialog(null, nombres_archivos, "Archivos elegidos", JOptionPane.PLAIN_MESSAGE);

        try{
            // Creamos el socket
            Integer PUERTO = 7000;
            ServerSocket server = new ServerSocket(PUERTO);
            server.setReuseAddress(true);
            System.out.println("Servicio iniciado...");

            // Esperar clientes
            while(Boolean.TRUE){
                System.out.println("Eperando conexion...");
                Socket client = server.accept();
                System.out.println("Conexión establecida desde"+client.getInetAddress()+":"+client.getPort());

                // Enviar parametros de configuracion
                DataInputStream dis = new DataInputStream(client.getInputStream());
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());

                /*
                dos.writeInt(longitud_paquete);
                dos.flush();

                dos.writeBoolean(algoritmo_nigle);
                dos.flush();

                // Enviar número de archivos a enviar
                dos.writeInt(numero_archivos);
                dos.flush();
                * */





                // Enviar archivos
                for (File archivo : archivos) {

                    String nombre = archivo.getName();
                    long tam = archivo.length();
                    long enviados = 0;
                    int porcentaje;
                    int n;
                    String ruta = archivo.getAbsolutePath();

                    // Despues mandamos el nombre del archivo
                    dos.writeUTF(nombre);
                    dos.flush();

                    // Y finalmente su tamaño
                    dos.writeLong(tam);
                    dos.flush();

                    System.out.format("Enviando el archivo: %s...\n", nombre);
                    System.out.format("Que esta en la ruta: %s\n", ruta);

                    while (enviados < tam) {
                        byte[] b = new byte[longitud_paquete];
                        n = dis.read(b);
                        dos.write(b, 0, n);
                        dos.flush();
                        enviados = enviados + n;
                        porcentaje = (int) (enviados * 100 / tam);
                        System.out.println("\rSe ha transmitido el: " + porcentaje + "% ...");
                    }

                    System.out.println("Archivo enviado");
                    dos.flush();

                    dos.close();
                    dis.close();
                }
                client.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
