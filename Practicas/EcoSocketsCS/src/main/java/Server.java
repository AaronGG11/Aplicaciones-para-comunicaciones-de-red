import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static final int PORT = 4444;
    public static void main(String[] args) throws IOException {
        // Establece el puerto en el que escucha peticiones
        ServerSocket socketServidor = null;
        try {
            socketServidor = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("No puede escuchar en el puerto: " + PORT);
            System.exit(-1);
        }

        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;

        System.out.println("Escuchando: " + socketServidor);
        try {
            while(true) {
                // Se bloquea hasta que recibe alguna petición de un cliente
                // abriendo un socket para el cliente
                socketCliente = socketServidor.accept();
                System.out.println("Connexión acceptada: " + socketCliente);
                // Establece canal de entrada
                entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                // Establece canal de salida
                salida = new PrintWriter(new BufferedWriter(new
                        OutputStreamWriter(socketCliente.getOutputStream())), true);

                // Hace eco de lo que le proporciona el cliente, hasta que recibe "Adios"
                //while (true) {
                String str = entrada.readLine();
                System.out.println("Cliente: " + str);
                salida.println(str);
                //if (str.equals("Adios")) break;
                //}
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        salida.close();
        entrada.close();
        socketCliente.close();
        socketServidor.close();
    }
}