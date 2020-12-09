package model;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public class Servidor {
    public static void main(String[] args) {
        // Path de carpeta con imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("customer_resources");
        images_path.append(File.separator);

        String outputFile = images_path.toString() + "imagen_cliente.zip", host = "127.0.0.1";
        int port = 8001, bufferSize = 20000000;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            Selector selector = Selector.open();
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(host, port));
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Servidor iniciado...");
            while(true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while ( iterator.hasNext() ) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        continue;
                    }

                    int counter = 1;
                    if ( key.isReadable() ) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        FileOutputStream os = new FileOutputStream(outputFile);
                        FileChannel destination = os.getChannel();
                        int res;
                        while( ( res = channel.read(buffer) ) != -1){
                            counter += res;
                            System.out.println("Leyendo bloque de "+res+" Bytes");
                            buffer.flip();
                            while( buffer.hasRemaining() ){
                                destination.write(buffer);
                            }
                            buffer.clear();
                        }
                        channel.close();
                        destination.close();
                        os.close();
                        System.out.println("Recibidos: " + counter+ " Bytes");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}