package model;

import Utilidades.Archivos;
import Utilidades.Memorama;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        tipo_mensaje.add("img"); // imagenes de juego
        tipo_mensaje.add("mov"); // movimeinto de juego
        tipo_mensaje.add("ini"); // inicio de jeugo
        tipo_mensaje.add("tip"); // tipo juego
        tipo_mensaje.add("fin"); // fin de juego

        // TODO : Instancia de memorama
        Memorama memorama = new Memorama();

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

                    // TODO : ES CONECTABLE
                    if( key.isConnectable() ) {
                        SocketChannel client = (SocketChannel) key.channel();

                        if( client.isConnectionPending() ) {
                            System.out.println("Intentando establecer conexion");
                            try {
                                client.finishConnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("Conexión establecida correctamente");

                        // TODO : Pedir modo de juego
                        memorama.setPuerto(""+client.socket().getLocalPort());
                        memorama.setTipo_juego(Memorama.obtenerModoJuego(memorama.getPuerto()));

                        System.out.println("Seleccion de modo de juego -> " + memorama.getTipo_juego());

                        client.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ);
                        continue;
                    }

                    // TODO : ES ESCRIBIBLE
                    int counter = 0;
                    if( key.isWritable() ){
                        SocketChannel client = (SocketChannel) key.channel();

                        if(!memorama.getHay_imagenes()){
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(0).getBytes()));

                            System.out.println("Solicitando imagenes al servidor");
                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(memorama.getRecibir_tipo()){
                            if(memorama.getTipo_juego().equals("Solitario")){
                                client.write(ByteBuffer.wrap((tipo_mensaje.get(3) + new String("uno")).getBytes()));
                            }
                            if(memorama.getTipo_juego().equals("Pareja")){
                                client.write(ByteBuffer.wrap((tipo_mensaje.get(3) + new String("duo")).getBytes()));
                            }

                            System.out.println("Informando al servidor el modo de juego");
                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(memorama.getTerminar_juego()){
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(4).getBytes()));
                            System.out.println("Informando al servidor le ternimo del juego");

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        continue;

                    } else if(key.isReadable()){ // TODO : ES LEIBLE
                        try {
                            SocketChannel channel = (SocketChannel) key.channel();

                            ByteBuffer tipo = ByteBuffer.allocate(3);
                            channel.read(tipo);
                            tipo.flip();
                            String tipo_msg = new String(tipo.array(),0,3);
                            System.out.println("Tipo de mensaje: " + tipo_msg);
                            tipo.clear();


                            if(tipo_msg.equals("img")){
                                FileOutputStream os = new FileOutputStream(outputFile);
                                FileChannel destination = os.getChannel();
                                int res;

                                int contador_imagenes = 0;
                                while( ( (res = channel.read(buffer) ) != -1) ){
                                    counter += res;
                                    System.out.println("Leyendo bloque de " + res + " Bytes");
                                    buffer.flip();
                                    while( buffer.hasRemaining() ){
                                        destination.write(buffer);
                                    }
                                    buffer.clear();
                                    if(counter == 16206554){break;}
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
                                memorama.setHay_imagenes(Boolean.TRUE);
                                System.out.println("Imagenes recibidas correctamente");

                                // TODO : Mostrar tablero


                                // TODO : Enviar al servidor el tipo de juego
                                memorama.setRecibir_tipo(Boolean.TRUE);
                            }

                            if(tipo_msg.equals("tip")){
                                System.out.println("El servidor registro el modo de juego");
                                memorama.setRecibir_tipo(Boolean.FALSE);

                                // TODO : Solicitar inicar juego sin importar modo de juego
                                // A LA ESPERA DEL BOTON INCIAR
                                memorama.configurarTablero(images_path.toString());
                                memorama.implementsListener();

                                if(memorama.getTipo_juego().equals("Solitario")){
                                    while(!memorama.getSolicitar_inicio()){
                                        continue;
                                    }
                                    // TODO : Cerrar el socket channel
                                    memorama.setTerminar_juego(Boolean.TRUE);
                                    System.out.println("Solicitando iniciar el juego");
                                }else if(memorama.getTipo_juego().equals("Pareja")){
                                    // Habilita boton de inicar solo hasta que
                                }
                            }

                            key.interestOps(SelectionKey.OP_WRITE);
                            continue;
                            //channel.close();
                        }catch (IOException io){}
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}