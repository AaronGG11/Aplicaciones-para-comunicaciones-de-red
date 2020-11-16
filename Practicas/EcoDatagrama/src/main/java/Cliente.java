import java.net.*;
import java.io.*;
import java.util.Hashtable;
import java.util.Map;

public class Cliente {
    public static void main(String[] args){
        String host = "127.0.0.1";
        Integer puerto = 2000;
        Integer longitud_paquete = 5;

        String mensaje_fin = "FINDECONECCION";
        byte[] byte_mensaje_fin = mensaje_fin.getBytes();

        Map<String, StringBuilder> mensajes = new Hashtable<>();

        try{
            DatagramSocket cl = new DatagramSocket();
            System.out.print("Cliente iniciado, escriba un mensaje de saludo:");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();
            byte[] byte_mensaje = mensaje.getBytes();

            for(int i=0; i<byte_mensaje.length; i+=longitud_paquete ){
                DatagramPacket p = new DatagramPacket(byte_mensaje, i , longitud_paquete,InetAddress.getByName(host), puerto);
                cl.send(p);
            }

            DatagramPacket p = new DatagramPacket(byte_mensaje_fin,byte_mensaje_fin.length,InetAddress.getByName(host), puerto);
            cl.send(p);

            cl.close();
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}