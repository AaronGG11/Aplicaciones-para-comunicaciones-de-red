import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args) {
        try{
            // Se crea el socket
            ServerSocket s = new ServerSocket(1234);
            System.out.println("Esperando cliente ...");
            // Iniciamos el ciclo infinito
            for(;;){
                // Tenemos un bloqueo, en el momento que llegue una conexión continua el programa
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde "+  cl.getInetAddress()+":" + cl.getPort());
                String mensaje ="Hola mundo";
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                // Se envía el mensaje
                pw.println(mensaje);
                // Se limpia le flujo
                pw.flush();
                pw.close();
                cl.close();
            }//for
        }catch(Exception e){ // Manejo de excepciones
            e.printStackTrace();
        }//catch

    }
}
