import java.net.*;
import java.util.Hashtable;
import java.util.Map;

public class Servidor {
    public static void main(String[] args){
        Integer puerto = 2000;
        Integer longitud_paquete = 20;

        Map<String, StringBuilder> mensajes = new Hashtable<>();

        try{
            DatagramSocket s = new DatagramSocket(puerto);
            System.out.println("Servidor iniciado, esperando cliente");
            for(;;){
                DatagramPacket p = new DatagramPacket(new byte[longitud_paquete],longitud_paquete);
                s.receive(p);

                if(!mensajes.containsKey(p.getAddress()+":"+p.getPort()))
                    mensajes.put(p.getAddress()+":"+p.getPort(), new StringBuilder());

                if(!(new String(p.getData(),0,p.getLength())).equals("FINDECONECCION")){ // no es fin de coneccion
                    mensajes.get(p.getAddress()+":"+p.getPort()).append(new String(p.getData(),0,p.getLength()));
                }else{ // es fin de coneccion
                    System.out.println("Datagrama recibido desde"+p.getAddress()+":"+p.getPort());
                    String mensaje = mensajes.get(p.getAddress()+":"+p.getPort()).toString();
                    System.out.println("Con el mensaje: "+ mensaje);
                }
            }//for
            //s.close()
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}