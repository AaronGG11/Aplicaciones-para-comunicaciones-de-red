package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import serviciodechat.Cliente;

/**
 *
 * @author aaron
 */
public class SuperNode implements Runnable{
    private final String IP = "230.1.1.1";
    private final int PUERTO = 4000;
    private MulticastSocket cl;
    private DatagramPacket packet;
    private byte b[];
    private InetAddress grupo = null;
    public final int TAM_BUFFER = 100;
    
    private final String INICIO          = "<inicio>";
    private final String MENSAJE_PRIVADO = "<privado>";
    private final String MENSAJE_PUBLICO = "<msj>";
    private final String MENSAJE_GRUPO   = "<grupo>";
    private final String FIN             = "<fin>";
    public final int DESCONOCIDO_ID     = 0;
    public final int INICIO_ID          = 1;
    public final int MENSAJE_PRIVADO_ID = 2;
    public final int MENSAJE_PUBLICO_ID = 3;
    public final int FIN_ID             = 4;
    public final int MENSAJE_GRUPO_ID   = 5;
    
    private SuperNode() {
        try
        {
            cl = new MulticastSocket(PUERTO);
            System.out.println("Super Nodo conectado desde: " + cl.getLocalPort());
            cl.setReuseAddress(true);
            cl.setTimeToLive(1);
            grupo = InetAddress.getByName(IP);
            cl.joinGroup(grupo);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                actualizar();
            } catch (Exception ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public static void main(String args[]) throws IOException {
        SuperNode sp =new SuperNode();
        Runnable run = sp;
        Thread thread = new Thread(sp);
        thread.start();
    }

    private void actualizar() {
        
    }
    
    

}
