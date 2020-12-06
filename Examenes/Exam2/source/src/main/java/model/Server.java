package model;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Server { // Sockets TCP (de flujo) NO BLOQUEANTES
    public static void main(String[] args) {
        Integer puerto = 9000;

        // Orden de imagenes
        Boolean bandera_orden = Boolean.TRUE;
        List<String> imagenes_orden = new ArrayList<>();
        imagenes_orden = Utilidades.obtenerOrdenImagenes(bandera_orden);

        // Path de carpeta con imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("clients");
        images_path.append(File.separator);
        //images_path.append("fondo.jpg");


        try{
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(puerto));
            System.out.println("Esperando clientes...");
            Selector selector = Selector.open();

            while(true){
                selector.select(); // Esperar a tener un evento
                Iterator<SelectionKey> iterador = selector.selectedKeys().iterator();

                while(iterador.hasNext()){
                    SelectionKey clave = (SelectionKey)iterador.next();
                    iterador.remove();

                    if(clave.isAcceptable()){
                        SocketChannel cliente = ssc.accept();
                        System.out.println("Cliente conectado desde " + cliente.socket().getInetAddress() + ":"+ cliente.socket().getPort());
                        cliente.configureBlocking(false);
                        cliente.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                    }

                    if(clave.isReadable()){

                    }

                    if(clave.isWritable()){

                    }
                }
            }

        }catch(Exception e){

        e.printStackTrace();
        }
    }
}
