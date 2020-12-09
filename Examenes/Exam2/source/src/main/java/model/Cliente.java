package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Cliente {

    public static void main( String[] args ) {

        // Path de carpeta con imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("server_resources");
        images_path.append(File.separator);

        String inputFile = images_path+"images.zip" , host = "127.0.0.1";
        int port = 8001, bufferSize = 20000000;

        try {
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            byte[] byteArr = new byte[buffer.capacity()];

            Selector selector = Selector.open();
            SocketChannel connectionClient = SocketChannel.open();
            connectionClient.configureBlocking(false);
            connectionClient.connect( new InetSocketAddress(host, port) );
            connectionClient.register(selector, SelectionKey.OP_CONNECT);

            while(true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while ( iterator.hasNext() ) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    iterator.remove();
                    SocketChannel client = (SocketChannel) key.channel();
                    if( key.isConnectable() ) {
                        if( client.isConnectionPending() ) {
                            System.out.println("Intentando establecer la conexion");
                            try {
                                client.finishConnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        client.register(selector, SelectionKey.OP_WRITE);
                        continue;
                    }

                    if( key.isWritable() ){
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
                        return;
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}