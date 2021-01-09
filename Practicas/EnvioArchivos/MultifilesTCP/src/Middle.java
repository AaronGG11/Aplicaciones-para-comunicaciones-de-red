import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Aaron Garcia
 */

public class Middle {
    public static void main(String[] args) {
        // This is a client and a server
        StringBuilder LOCAL_PATH = new StringBuilder();
        LOCAL_PATH.append(".");
        LOCAL_PATH.append(File.separator);
        LOCAL_PATH.append("middle_files");
        LOCAL_PATH.append(File.separator);

        // Client part
        // Server's credentials to connect
        String HOST = "localhost";
        Integer PORT = 7000;

        try {
            Socket cliente = new Socket(HOST, PORT);


            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Server part
        Integer LOCAL_PORT = 7000;

        try {
            ServerSocket server = new ServerSocket(LOCAL_PORT);
            server.setReuseAddress(true);
            System.out.println("Servicio iniciado...");

            while(Boolean.TRUE){
                System.out.println("Esperando conexion...");

                Socket client = server.accept();
                System.out.println("Cliente conectado desde: " + client.getInetAddress() + " : " + client.getPort());



                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
