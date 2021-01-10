package sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author tona
 */
public class Servidor {
    private static final int PUERTO = 9999;

    public static void main(String[] args) throws IOException, InterruptedException {
        StringBuilder LOCAL_PATH = new StringBuilder();
        LOCAL_PATH.append(".");
        LOCAL_PATH.append(File.separator);
        LOCAL_PATH.append("middle_files");
        LOCAL_PATH.append(File.separator);


        ServerSocket s = new ServerSocket(PUERTO);
        s.setReuseAddress(true);
        System.out.println("Servicio iniciado...");
        while (true ) {
            System.out.println("Eperando conexion...");
            Socket cl = s.accept();

            System.out.format("Cliente conectado desde: %s:%s\n", cl.getInetAddress(), cl.getPort());
            DataInputStream dis = new DataInputStream(cl.getInputStream());
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
            Integer tipo = dis.readInt();

            if(tipo == 1){ // cliente tipo #1 -> incial
                String ruta = dis.readUTF();
                String nombre = dis.readUTF();

                // recibir archivo
                escribirArchivo(LOCAL_PATH + nombre, dis);

                System.out.println("¡Archivo recibido!\n");
            }else{ // cliente tipo #2 -> final
                System.out.println("Cliente final conectado");

                File carpeta = new File(LOCAL_PATH.toString());
                String[] listado = carpeta.list();
                if (listado == null || listado.length == 0) {
                    System.out.println("No hay elementos dentro de la carpeta actual");
                }
                else {
                    Integer numero_archivos = listado.length;

                    dos.writeInt(numero_archivos);
                    dos.flush();

                    for (int i=0; i< listado.length; i++) {
                        File archivo = new File(LOCAL_PATH + listado[i]);

                        String nombre_archivo = archivo.getName();
                        String ruta_archivo = archivo.getAbsolutePath();
                        Long longitud_archivo = archivo.length();


                        dos.writeUTF(nombre_archivo);
                        dos.flush();

                        dos.writeLong(longitud_archivo);
                        dos.flush();

                        // Vas a escribir al mismo dos
                        long enviados = 0;
                        int porcentaje;
                        int n;
                        String ruta = archivo.getAbsolutePath();

                        DataInputStream dis_archivo = new DataInputStream(new FileInputStream(ruta));

                        while (enviados < longitud_archivo) {
                            byte[] b = new byte[1500];
                            n = dis_archivo.read(b);
                            dos.write(b, 0, n);
                            dos.flush();
                            enviados = enviados + n;
                            porcentaje = (int) (enviados * 100 / longitud_archivo);
                            System.out.println("\rSe ha transmitido el: " + porcentaje + "% ...");
                        }
                        dis_archivo.close();
                        Thread.sleep(200);
                    }
                }
            }
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
                byte[] buffer = new byte[1024];
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
