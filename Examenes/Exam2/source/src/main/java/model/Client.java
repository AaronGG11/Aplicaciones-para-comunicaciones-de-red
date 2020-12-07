package model;

import java.nio.channels.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class Client { // Sockets TCP (de flujo) NO BLOQUEANTES
    public static void main(String[] args){
        String host = "127.0.0.1";
        Integer puerto = 9000;

        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("customers");
        images_path.append(File.separator);

        try{
            InetSocketAddress dst = new InetSocketAddress(host,puerto);
            SocketChannel cl = SocketChannel.open();

            cl.configureBlocking(false);

            Selector sel = Selector.open();
            cl.register(sel, SelectionKey.OP_CONNECT);
            cl.connect(dst);

            while(true){
                sel.select();
                Iterator<SelectionKey>it = sel.selectedKeys().iterator();
                while(it.hasNext()){
                    SelectionKey k = (SelectionKey)it.next();
                    it.remove();
                    if(k.isConnectable()){
                        SocketChannel ch = (SocketChannel)k.channel();
                        if(ch.isConnectionPending()){
                            System.out.println("Estableciendo conexion con el servidor... espere..");
                            try{
                                ch.finishConnect(); // Esperar a que termine la conexion
                            }catch(Exception e){
                                e.printStackTrace();
                            }//catch
                            System.out.println("Conexion establecida...");
                        }//if
                        ch.register(sel, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                        Utilidades.crearCarpeta(""+cl.socket().getPort());
                        continue;
                    }//if
                    if(k.isReadable()){
                        System.out.println("es leible");
                        continue;
                    } else if(k.isWritable()){
                        System.out.println("es escribible");
                        continue;
                    } //if
                }//while
            }//while
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}
