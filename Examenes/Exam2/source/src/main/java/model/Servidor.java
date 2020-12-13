package model;

import Utilidades.Memorama;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalTime;
import java.util.*;


public class Servidor {
    public static void main(String[] args) {
        // TODO : Path de imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("server_resources");
        images_path.append(File.separator);

        // TODO : Tipos de mensajes
        List<String> tipo_mensaje = new ArrayList<>();
        tipo_mensaje.add("img"); // imagenes de juego
        tipo_mensaje.add("mov"); // movimeinto de juego
        tipo_mensaje.add("ini"); // inicio de jeugo
        tipo_mensaje.add("tip"); // tipo juego
        tipo_mensaje.add("fin"); // fin de juego

        // TODO : Control de jugadores
        Map<SocketChannel, Memorama> jugadores = new Hashtable<>();

        String inputFile = images_path.toString() + "images.zip", host = "127.0.0.1";
        int port = 9000, bufferSize = 20000000;

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

                        jugadores.put(client,new Memorama());
                        jugadores.get(client).setPuerto(""+client.socket().getPort());

                        System.out.println("Cliente " + client.getRemoteAddress() + " registrado");
                        continue;
                    }

                    // TODO : ES LEIBLE
                    if ( key.isReadable() ) {
                        SocketChannel channel = (SocketChannel) key.channel();

                        // TODO : Capturar tipo de mensaje
                        ByteBuffer tipo = ByteBuffer.allocate(3);
                        channel.read(tipo);
                        String tipo_msg = new String(tipo.array(),0,3);
                        tipo.flip();

                        if(tipo_msg.equals("img")) {
                            System.out.println("Cliente " + channel.getRemoteAddress() + " solicita imagenes");
                            jugadores.get(channel).setRecibir_imagenes(Boolean.TRUE);
                        }

                        if(tipo_msg.equals("tip")){
                            channel.read(tipo);
                            String modo_juego = new String(tipo.array(),0,3);
                            tipo.flip();

                            if(modo_juego.equals("uno")){jugadores.get(channel).setTipo_juego("Solitario");}
                            if(modo_juego.equals("duo")){jugadores.get(channel).setTipo_juego("Pareja");}


                            jugadores.get(channel).setRecibir_tipo(Boolean.TRUE);

                            System.out.println("Cliente " + channel.getRemoteAddress() + " avisa modo juego -> " + jugadores.get(channel).getTipo_juego());
                        }

                        if(tipo_msg.equals("mov")){
                            System.out.println("Cliente " + channel.getRemoteAddress() + " envia movimiento");
                        }

                        if(tipo_msg.equals("fin")){
                            jugadores.get(channel).setTerminar_juego(Boolean.TRUE);
                            System.out.println("Cliente " + channel.getRemoteAddress() + " va a terminar juego");
                        }

                        key.interestOps(SelectionKey.OP_WRITE);

                    }else if (key.isWritable()){ // TODO : ES ESCRIBIBLE
                        SocketChannel client = (SocketChannel) key.channel();

                        if(jugadores.get(client).getRecibir_imagenes()){ // Solicitud de imagenes
                            FileInputStream is = new FileInputStream( inputFile );
                            FileChannel source = is.getChannel();

                            int res;
                            int counter = 0;
                            buffer.clear();

                            // TODO : Enviar tipo de mensaje
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(0).getBytes()));

                            while( (( res = source.read(buffer) ) != -1 )){
                                System.out.println("Leyendo " + res + " bytes");
                                counter += res;

                                buffer.flip();
                                while( buffer.hasRemaining() ){
                                    client.write(buffer);
                                }
                                buffer.clear();
                            }
                            source.close();
                            is.close();

                            jugadores.get(client).setRecibir_imagenes(Boolean.FALSE);
                            System.out.println("Enviados: " + counter + " Bytes");
                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(jugadores.get(client).getRecibir_tipo()){
                            // TODO : Enviar tipo de mensaje
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(3).getBytes()));
                            System.out.println("Avisando al cliente que se registro modo de juego");
                            jugadores.get(client).setRecibir_tipo(Boolean.FALSE);
                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(jugadores.get(client).getTerminar_juego()){
                            // TODO : Enviar tipo de mensaje
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(4).getBytes()));
                            System.out.println("Enviando hora final de juego");
                            jugadores.get(client).setHora_fin(LocalTime.now());
                            client.write(ByteBuffer.wrap(jugadores.get(client).getHora_fin().toString().getBytes())); // 15
                            System.out.println("Cliente " + client.getRemoteAddress() + " termino su ejecucion");
                            client.close();

                        }

                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}