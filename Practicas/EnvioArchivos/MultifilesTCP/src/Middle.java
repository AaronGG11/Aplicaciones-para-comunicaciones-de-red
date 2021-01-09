import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Aaron Garcia
 */

public class Middle {
    public static void main(String[] args) {
        // Configuration values
        Integer longitud_buffer = null;
        Integer numero_archivos = null;
        Boolean algoritmo_nigle = null;


        // This is a client and a server
        StringBuilder LOCAL_PATH = new StringBuilder();
        LOCAL_PATH.append(".");
        LOCAL_PATH.append(File.separator);
        LOCAL_PATH.append("middle_files");
        LOCAL_PATH.append(File.separator);

        String nombre = new String();

        // Client part
        // Server's credentials to connect
        String HOST = "localhost";
        Integer PORT = 7000;

        try {
            Socket cliente = new Socket(HOST, PORT);
            System.out.println("Cliente: " + cliente.getLocalSocketAddress() + " conectado");

            DataInputStream dis = new DataInputStream(cliente.getInputStream());

            longitud_buffer = dis.readInt();
            numero_archivos = dis.readInt();
            algoritmo_nigle = dis.readBoolean();

            System.out.println(longitud_buffer);
            System.out.println(numero_archivos);
            System.out.println(algoritmo_nigle);


            byte[] b = new byte[1024];
            nombre = dis.readUTF();
            System.out.println("Recibimos el archivo:"+nombre);
            long tam = dis.readLong();
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(LOCAL_PATH + nombre));
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
            System.out.println("Archivo recibido.\n");
            dos.close();
            dis.close();

            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Server part
        Integer LOCAL_PORT = 7001;
        System.out.println();

        try {
            ServerSocket server = new ServerSocket(LOCAL_PORT);
            server.setReuseAddress(true);
            System.out.println("Servicio iniciado...");

            while(Boolean.TRUE){
                System.out.println("Esperando conexion...");

                Socket client = server.accept();
                System.out.println("Cliente conectado desde: " + client.getInetAddress() + " : " + client.getPort());

                File f = new File(LOCAL_PATH + nombre);
                String archivo = f.getAbsolutePath(); //Dirección
                String nombre_local = f.getName(); //Nombre
                Long tam = f.length();  //Tamaño

                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(archivo));

                dos.writeInt(longitud_buffer);
                dos.flush();

                dos.writeInt(numero_archivos);
                dos.flush();

                dos.writeBoolean(algoritmo_nigle);
                dos.flush();

                dos.writeUTF(nombre_local);
                dos.flush();

                dos.writeLong(tam);
                dos.flush();

                byte[] b = new byte[1024];
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