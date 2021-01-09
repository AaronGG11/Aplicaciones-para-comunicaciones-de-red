import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Aaron Garcia
 */

public class Initial {
    public static void main(String[] args) {
        // This is just a server
        Integer PORT = 7000;

        try {
            ServerSocket server = new ServerSocket(PORT);
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
