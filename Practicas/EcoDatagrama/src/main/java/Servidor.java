import java.net.*;
import java.util.Hashtable;
import java.util.Map;

public class Servidor {
    public static void main(String[] args){
        String host = "127.0.0.1";
        Integer puerto = 2000;
        Integer longitud_paquete = 20;

        String mensaje_fin = "FINDECONECCION"; // longitud(mensaje_fin) <= longitud_paquete
        byte[] byte_mensaje_fin = mensaje_fin.getBytes();

        Map<String, StringBuilder> mensajes = new Hashtable<>();

        try{
            DatagramSocket s = new DatagramSocket(puerto);
            System.out.println("Servidor iniciado, esperando cliente");
            for(;;){
                DatagramPacket p = new DatagramPacket(new byte[longitud_paquete],longitud_paquete);
                s.receive(p);

                // Agregar llave
                if(!mensajes.containsKey(p.getAddress()+":"+p.getPort()))
                    mensajes.put(p.getAddress()+":"+p.getPort(), new StringBuilder());

                // Agregar paquetes
                if(!(new String(p.getData(),0,p.getLength())).equals(mensaje_fin)){ // no es fin de coneccion
                    mensajes.get(p.getAddress()+":"+p.getPort()).append(new String(p.getData(),0,p.getLength()));
                    System.out.println("\tRecibiendo: " + new String(p.getData(),0,p.getLength()));
                }else{ // es fin de coneccion
                    System.out.println("\nDatagrama recibido desde"+p.getAddress()+":"+p.getPort());
                    String mensaje = mensajes.get(p.getAddress()+":"+p.getPort()).toString();
                    System.out.println("Con el mensaje: "+ mensaje);

                    // Ahora va a enviar el mensaje de echo segmentado
                    String mensaje_echo = mensajes.get(p.getAddress()+":"+p.getPort()).toString();

                    byte[] byte_mensaje_echo = mensaje_echo.getBytes();

                    // Envia paquetes de 20 bytes
                    for(int i=0; i<byte_mensaje_echo.length; i+=longitud_paquete ){
                        if((i+longitud_paquete) > byte_mensaje_echo.length){
                            DatagramPacket echo = new DatagramPacket(byte_mensaje_echo, i , byte_mensaje_echo.length - i,InetAddress.getByName(host), p.getPort());
                            System.out.println("\tEnviando: " + new String(echo.getData(),echo.getOffset(),echo.getLength()));
                            s.send(echo);
                        } else{
                            DatagramPacket echo = new DatagramPacket(byte_mensaje_echo, i , longitud_paquete,InetAddress.getByName(host), p.getPort());
                            System.out.println("\tEnviando: " + new String(echo.getData(),echo.getOffset(),echo.getLength()));
                            s.send(echo);
                        }
                        //DatagramPacket echo = new DatagramPacket(byte_mensaje_echo, i , longitud_paquete,InetAddress.getByName(host), p.getPort());
                        //System.out.println(echo.getData());
                        //s.send(echo);
                    }

                    // Envia bandera de termino de mensaje completo
                    DatagramPacket echo = new DatagramPacket(byte_mensaje_fin,byte_mensaje_fin.length,InetAddress.getByName(host), p.getPort());
                    s.send(echo);

                    mensajes.remove(p.getAddress()+":"+p.getPort());
                    System.out.println("ECHO servidor -> cliente enviado");
                }
            }//for
            //s.close()
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}