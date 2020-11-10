import java.net.*;

public class SMulticastB {
    public static void main(String[] args ){
        InetAddress gpo;
        try{
            MulticastSocket s= new MulticastSocket(9876);
            s.setReuseAddress(true);
            s.setTimeToLive(1);
            String msj ="hola";
            byte[] b = msj.getBytes();
            gpo = InetAddress.getByName("228.1.1.1");
            s.joinGroup(gpo);
            for(;;){
                DatagramPacket p = new DatagramPacket(b,b.length,gpo,9999);
                s.send(p);
                System.out.println("Enviando mensaje "+msj+ " con un TTL= "+s.getTimeToLive());
                try{
                    Thread.sleep(3000);
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }//for
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}
