import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;


public class Client {
    public final static int PUERTO = 7;
    private final static int LIMITE = 100;
    public static void main(String[] args) {
        boolean bandera=false;
        SocketAddress remoto = new InetSocketAddress("127.0.0.1",PUERTO);
        try{
            DatagramChannel canal = DatagramChannel.open();
            canal.configureBlocking(false);
            canal.connect(remoto);
            Selector selector = Selector.open();
            canal.register(selector,SelectionKey.OP_WRITE);
            ByteBuffer buffer = ByteBuffer.allocateDirect(4);
            int n = 0;
            while(true){
                selector.select(5000); //espera 5 segundos por la conexión
                Set sk = selector.selectedKeys();
                if(sk.isEmpty() && n == LIMITE || bandera){
                    canal.close();
                    break;
                }else{
                    Iterator it = sk.iterator();
                    while(it.hasNext()){
                        SelectionKey key = (SelectionKey)it.next();
                        it.remove();
                        if(key.isWritable()){
                            buffer.clear();
                            buffer.putInt(n);
                            buffer.flip();
                            canal.write(buffer);
                            System.out.println("Escribiendo el dato: "+n);
                            n++;
                            if(n==LIMITE){
                                //todos     los paquetes han sido escritos;
                                buffer.clear();
                                buffer.putInt(100);
                                buffer.flip();
                                canal.write(buffer);
                                bandera = true;
                                key.interestOps(SelectionKey.OP_READ);
                                break;
                            }//if
                        }//if
                    }//while
                }//else
            }//while
        }catch(Exception e){
            System.err.println(e);
        }//catch
    }//main

}
