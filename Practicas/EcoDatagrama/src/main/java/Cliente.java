import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args){
        try{
            DatagramSocket cl = new DatagramSocket();
            System.out.print("Cliente iniciado, escriba un mensaje de saludo:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String mensaje = br.readLine();
            byte[] b = mensaje.getBytes();

            String dst = "127.0.0.1";
            int pto = 2000;

            DatagramPacket p;

            if(b.length <= 20){
                p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),pto);
            }else{
                int total_paquetes = Utilidades.calcularNumeroPaquetes(b.length,20);

                for(int i=0; i<total_paquetes; i++){
                    if(i == total_paquetes-1)
                        p = new DatagramPacket(b, i*20, b.length - (i*20), InetAddress.getByName(dst),pto);
                    else
                        p = new DatagramPacket(b, i*20, 20, InetAddress.getByName(dst),pto);

                    cl.send(p);
                }
            }
            cl.close();
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}

/*
* try{
            DatagramSocket cl = new DatagramSocket();
            System.out.print("Cliente iniciado, escriba un mensaje de saludo:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();
            byte[] b = mensaje.getBytes();
            String dst = "127.0.0.1";
            int pto = 2000;
            DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),pto);
            cl.send(p);
            cl.close();
        }catch(Exception e){
            e.printStackTrace();
        }//catch
*
* */
