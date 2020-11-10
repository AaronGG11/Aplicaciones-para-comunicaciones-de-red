import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args)  throws IOException {
        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;
        BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));

        // Creamos un socket en el lado cliente, enlazado con un
        // servidor que está en la misma máquina que el cliente
        // y que escucha en el puerto 4444
        try {

            System.out.println("Escriba la dirección del servidor: ");
            String host = stdIn.readLine();
            System.out.printf("\nEscriba el puerto:");
            Integer puerto = Integer.parseInt(stdIn.readLine());



            socketCliente = new Socket(host, puerto);
            // Obtenemos el canal de entrada
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            // Obtenemos el canal de salida
            salida = new PrintWriter(new BufferedWriter(new
                    OutputStreamWriter(socketCliente.getOutputStream())),true);
        } catch (IOException e) {
            System.err.println("No puede establer canales de E/S para la conexión");
            System.exit(-1);
        }


        String linea;

        // El programa cliente no analiza los mensajes enviados por el
        // usario, simplemente los reenvía al servidor hasta que este
        // se despide con "Adios"
        try {
            //while (true) {
                // Leo la entrada del usuario
                System.out.printf("\nEscriba el mensaje eco:");
                linea = stdIn.readLine();
                // La envia al servidor
                salida.println(linea);
                // Envía a la salida estándar la respuesta del servidor
                linea = entrada.readLine();
                System.out.println("Respuesta servidor: " + linea);
                // Si es "Adios" es que finaliza la comunicación
                //if (linea.equals("Adios")) break;
            //}
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        // Libera recursos
        salida.close();
        entrada.close();
        stdIn.close();
        socketCliente.close();
    }
}
