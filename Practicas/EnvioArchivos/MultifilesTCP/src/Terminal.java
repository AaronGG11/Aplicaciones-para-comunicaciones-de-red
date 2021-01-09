import java.io.*;
import java.net.Socket;


/**
 * @author Aaron Garcia
 */

public class Terminal {
    public static void main(String[] args) {
        // This is just a client
        StringBuilder LOCAL_PATH = new StringBuilder();
        LOCAL_PATH.append(".");
        LOCAL_PATH.append(File.separator);
        LOCAL_PATH.append("terminal_files");
        LOCAL_PATH.append(File.separator);

        String nombre = new String();

        // Server's credentials to connect
        String HOST = "localhost";
        Integer PORT = 7001;

        try {
            Socket cliente = new Socket(HOST, PORT);
            System.out.println("Cliente: " + cliente.getLocalSocketAddress() + " conectado");


            DataInputStream dis = new DataInputStream(cliente.getInputStream());
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
            System.out.print("\n\nArchivo recibido.\n");
            dos.close();
            dis.close();

            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
