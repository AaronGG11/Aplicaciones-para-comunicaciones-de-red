package model;

import Utilidades.Archivos;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cliente {

    public static void main( String[] args ) {
        // Path de carpeta con imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("customer_resources");
        images_path.append(File.separator);

        // TODO : Tipos de mensajes
        List<String> tipo_mensaje = new ArrayList<>();
        tipo_mensaje.add("img");
        tipo_mensaje.add("mov");

        String outputFile = images_path+"images.zip" , host = "127.0.0.1";
        int port = 9000, bufferSize = 20000000;

        try {
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

            Selector selector = Selector.open();
            SocketChannel connectionClient = SocketChannel.open();
            connectionClient.configureBlocking(false);
            connectionClient.connect( new InetSocketAddress(host, port));
            connectionClient.register(selector, SelectionKey.OP_CONNECT);

            while(true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while ( iterator.hasNext() ) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    iterator.remove();

                    if( key.isConnectable() ) {
                        SocketChannel client = (SocketChannel) key.channel();

                        if( client.isConnectionPending() ) {
                            System.out.println("Intentando establecer la conexion");
                            try {
                                client.finishConnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        client.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ);
                        continue;
                    }

                    int counter = 0;
                    if( key.isWritable() ){
                        System.out.println("escribir");
                        key.interestOps(SelectionKey.OP_READ);
                    } else if(key.isReadable()){
                        SocketChannel channel = (SocketChannel) key.channel();
                        FileOutputStream os = new FileOutputStream(outputFile);
                        FileChannel destination = os.getChannel();

                        ByteBuffer tipo = ByteBuffer.allocate(3);
                        channel.read(tipo);
                        tipo.flip();
                        String algo = new String(tipo.array(),0,3);
                        System.out.println("Tipo de mensaje: " + algo);

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

                        destination.close();
                        os.close();
                        System.out.println("Recibidos: " + counter + " Bytes");

                        // TODO : Descomprimir zip recibido
                        Archivos.unzip(images_path.toString()+"images.zip" ,images_path.toString());
                        System.out.println("Carpeta descomprimida correctamente");

                        // TODO : Cambiar nombre de carpeta zip que se descomprimio
                        File sourceFile = new File(images_path.toString() + "images");
                        File destFile = new File(images_path.toString() + channel.socket().getLocalPort());

                        if (sourceFile.renameTo(destFile)) {
                            System.out.println("Carpeta renombrada correctamente");
                        } else {
                            System.out.println("Error al renombrar carpeta");
                        }

                        // TODO : Eliminar archivo zip recibido
                        Archivos.eliminarArchivo(images_path.toString() + "images.zip");



                        channel.close();
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}