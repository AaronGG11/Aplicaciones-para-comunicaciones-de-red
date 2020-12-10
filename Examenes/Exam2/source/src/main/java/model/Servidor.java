package model;

import java.io.File;
import java.io.FileInputStream;
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
        images_path.append("server_resources");
        images_path.append(File.separator);

        String inputFile = images_path.toString() + "images.zip", host = "127.0.0.1";
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
                        client.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                        continue;
                    }

                    if ( key.isReadable() ) {
                        System.out.println("leer");
                    }else if (key.isWritable()){
                        SocketChannel client = (SocketChannel) key.channel();
                        FileInputStream is = new FileInputStream( inputFile );
                        FileChannel source = is.getChannel();
                        int res;
                        int counter = 0;
                        buffer.clear();
                        while( ( res = source.read(buffer) ) != -1 ){
                            System.out.println("Leyendo "+res+" bytes");
                            //if ( res == -1 ) break;
                            counter += res;
                            //buffer.put(byteArr, 0, Math.min(res, buffer.limit()));
                            buffer.flip();
                            while( buffer.hasRemaining() ){
                                client.write(buffer);
                            }
                            buffer.clear();
                        }
                        source.close();
                        client.close();
                        is.close();
                        System.out.println("Leidos: " + counter+ " Bytes");

                        //key.interestOps(SelectionKey.OP_READ);
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}