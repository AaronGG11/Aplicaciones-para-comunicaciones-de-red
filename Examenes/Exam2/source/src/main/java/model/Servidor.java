package model;

import Servicio.JuegoPareja;
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
import java.util.concurrent.atomic.AtomicInteger;


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
        tipo_mensaje.add("img"); // 0 - imagenes de juego
        tipo_mensaje.add("mov"); // 1 - movimeinto de juego
        tipo_mensaje.add("ini"); // 2 - inicio de jeugo
        tipo_mensaje.add("tip"); // 3 - tipo juego
        tipo_mensaje.add("fin"); // 4 - fin de juego
        tipo_mensaje.add("ord"); // 5 - orden de imagenes
        tipo_mensaje.add("tur"); // 6 - turno
        tipo_mensaje.add("jug"); // 7 - solicitar pareja
        tipo_mensaje.add("mv1"); // 8 - movimiento de primer imagen
        tipo_mensaje.add("mv2"); // 9 - movimiento de segunda imagen


        // TODO : Control de jugadores
        Map<SocketChannel, Memorama> jugadores = new Hashtable<>(); // todos los jugadores
        Map<Integer, JuegoPareja> juegos_pareja= new Hashtable<>(); // juegos en tiempo real
        List<SocketChannel> lista_espera = new ArrayList<>();

        AtomicInteger contador_juegos = new AtomicInteger(0);

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
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("tip")){
                            channel.read(tipo);
                            String modo_juego = new String(tipo.array(),0,3);
                            tipo.flip();

                            if(modo_juego.equals("uno")){jugadores.get(channel).setTipo_juego("Solitario");}
                            if(modo_juego.equals("duo")){jugadores.get(channel).setTipo_juego("Pareja");}


                            jugadores.get(channel).setRecibir_tipo(Boolean.TRUE);

                            // TODO : Jugadores en pareja
                            if(jugadores.get(channel).getTipo_juego().equals("Pareja")){
                                if(lista_espera.isEmpty()){ // no hay en lista de espera
                                    lista_espera.add(channel);

                                    System.out.println("Cliente " + channel.getRemoteAddress() + " espera contrincante");
                                }else{ // hay en lista de espera
                                    contador_juegos.incrementAndGet();
                                    juegos_pareja.put(contador_juegos.get(),new JuegoPareja(contador_juegos.get()));

                                    juegos_pareja.get(contador_juegos.get()).setJugador_1(lista_espera.get(0));
                                    juegos_pareja.get(contador_juegos.get()).setJugador_2(channel);

                                    // Asignaciones a jugadores
                                    // Jugador #1
                                    jugadores.get(lista_espera.get(0)).setEs_mi_turno(Boolean.TRUE);
                                    jugadores.get(lista_espera.get(0)).setPuerto_contrincante("" + channel.socket().getPort());
                                    jugadores.get(lista_espera.get(0)).setImagenes_orden(juegos_pareja.get(contador_juegos.get()).getImagenes_orden());
                                    jugadores.get(lista_espera.get(0)).setTiene_pareja(Boolean.TRUE);
                                    jugadores.get(lista_espera.get(0)).setId_juego(contador_juegos.get());
                                    jugadores.get(lista_espera.get(0)).setEs_jugador_1(Boolean.TRUE);

                                    // Jugador #2 (actual)
                                    jugadores.get(channel).setEs_mi_turno(Boolean.TRUE);
                                    jugadores.get(channel).setPuerto_contrincante("" + lista_espera.get(0).socket().getPort());
                                    jugadores.get(channel).setImagenes_orden(juegos_pareja.get(contador_juegos.get()).getImagenes_orden());
                                    jugadores.get(channel).setTiene_pareja(Boolean.TRUE);
                                    jugadores.get(channel).setId_juego(contador_juegos.get());
                                    jugadores.get(channel).setEs_jugador_1(Boolean.FALSE);

                                    // Mensages de operaciones realizadas
                                    System.out.println("Se creo juego id=" + contador_juegos.get() + ", clientes: " + lista_espera.get(0).socket().getPort() + " VS " + channel.socket().getPort());

                                    // Remover de lista de espera
                                    System.out.println("Cliente "+ lista_espera.get(0).getRemoteAddress() +" removido de la lista de espera");
                                    lista_espera.remove(0);
                                }
                            }

                            System.out.println("Cliente " + channel.getRemoteAddress() + " avisa modo juego -> " + jugadores.get(channel).getTipo_juego());
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("mov")){
                            System.out.println("Cliente " + channel.getRemoteAddress() + " envia movimiento");
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("fin")){
                            jugadores.get(channel).setTerminar_juego(Boolean.TRUE);
                            System.out.println("Cliente " + channel.getRemoteAddress() + " va a terminar juego");
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("ini")){
                            // TODO : Revisar tipo de inicio para pareja
                            jugadores.get(channel).setSolicitar_inicio(Boolean.TRUE);
                            System.out.println("Cliente " + channel.getRemoteAddress() + " solicita iniciar juego");
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("fin")){
                            jugadores.get(channel).setTerminar_juego(Boolean.TRUE);
                            System.out.println("Cliente " + channel.getRemoteAddress() + " solicita terminar juego");
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("jug")){
                            jugadores.get(channel).setSolicitar_pareja(Boolean.TRUE);
                            System.out.println("Cliente " + channel.getRemoteAddress() + " solicita contrincante");
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("ord")){
                            jugadores.get(channel).setSolicitar_orden_imagenes(Boolean.TRUE);
                            System.out.println("Cliente " + channel.getRemoteAddress() + " solicita orden de imagenes");
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("tur")){
                            jugadores.get(channel).setSolicitar_turno(Boolean.TRUE);
                            System.out.println("Cliente " + channel.getRemoteAddress() + " solicita turno inicial");
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("mv1")){
                            // Captura imagen que hay que mover
                            ByteBuffer imagen = ByteBuffer.allocate(2);
                            channel.read(imagen);
                            imagen.flip();
                            String imagen_mov = new String(imagen.array(),0,2);
                            imagen.clear();

                            jugadores.get(channel).setEnviar_primer_imagen(Boolean.TRUE);
                            jugadores.get(channel).setImagen_a_voltear(imagen_mov);

                            // Ahora va a escribir la imagen para el oponente
                            System.out.println("Cliente " + channel.getRemoteAddress() + " envia movimiento de primer imagen -> " + imagen_mov);
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(tipo_msg.equals("mv2")){
                            // Captura imagen que hay que mover
                            ByteBuffer imagen = ByteBuffer.allocate(2);
                            channel.read(imagen);
                            imagen.flip();
                            String imagen_mov = new String(imagen.array(),0,2);
                            imagen.clear();

                            jugadores.get(channel).setEnviar_segunda_imagen(Boolean.TRUE);
                            jugadores.get(channel).setImagen_a_voltear(imagen_mov);

                            // Ahora va a escribir la imagen para el oponente
                            System.out.println("Cliente " + channel.getRemoteAddress() + " envia movimiento de segunda imagen -> " + imagen_mov);
                            key.interestOps(SelectionKey.OP_WRITE);
                        }


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
                            System.out.println("Modo de juego registrado para el cliente " + client.getRemoteAddress());
                            jugadores.get(client).setRecibir_tipo(Boolean.FALSE);
                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(jugadores.get(client).getSolicitar_inicio()){
                            // TODO : Enviar tipo de mensaje
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(2).getBytes()));
                            jugadores.get(client).setHora_inicio(LocalTime.now());
                            System.out.println("Registro de hora de incio " + jugadores.get(client).getHora_inicio().toString() + " para cliente " + client.getRemoteAddress());
                            jugadores.get(client).setSolicitar_inicio(Boolean.FALSE);
                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(jugadores.get(client).getTerminar_juego()){
                            // TODO : Enviar tipo de mensaje
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(4).getBytes()));
                            System.out.println("Registro de hora final de juego");
                            jugadores.get(client).setHora_fin(LocalTime.now());
                            jugadores.get(client).setTerminar_juego(Boolean.FALSE);
                            System.out.println("Cliente " + client.getRemoteAddress() + " termino su ejecucion");
                            client.close();
                        }

                        if(jugadores.get(client).getSolicitar_pareja() &&
                            jugadores.get(client).getTipo_juego().equals("Pareja") &&
                                jugadores.get(client).getTiene_pareja()){

                            // TODO : Enviar tipo de mensaje jugador
                            client.write(ByteBuffer.wrap((tipo_mensaje.get(7) + jugadores.get(client).getPuerto_contrincante()).getBytes()));
                            jugadores.get(client).setSolicitar_pareja(Boolean.FALSE);

                            // TODO : Enviar puerto de contrincante

                            System.out.println("Enviando al cliente " + client.getRemoteAddress() + ", el puerto de su contrincante -> " + jugadores.get(client).getPuerto_contrincante());
                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(jugadores.get(client).getSolicitar_orden_imagenes()){
                            jugadores.get(client).setSolicitar_orden_imagenes(Boolean.FALSE);

                            // TODO : Enviar tipo de mensaje y string con orden de imagenes[longitud 501]
                            client.write(ByteBuffer.wrap((tipo_mensaje.get(5)+
                                    juegos_pareja.get(jugadores.get(client).getId_juego()).getImagenes_orden()).getBytes()));

                            System.out.println("Enviando al cliente " + client.getRemoteAddress() + " el orden de imagenes + longitug " + juegos_pareja.get(jugadores.get(client).getId_juego()).getImagenes_orden());

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(jugadores.get(client).getSolicitar_turno()){
                            jugadores.get(client).setSolicitar_turno(Boolean.FALSE);

                            // TODO : Enviar tipo de mensaje y 0 -> si no es turno, 1 -> si es turno
                            if(jugadores.get(client).getEs_jugador_1()){
                                client.write(ByteBuffer.wrap((tipo_mensaje.get(6) + "1").getBytes()));
                                System.out.println("Enviando al cliente " + client.getRemoteAddress() + " turno activo");
                            }
                            else{
                                client.write(ByteBuffer.wrap((tipo_mensaje.get(6) + "0").getBytes()));
                                System.out.println("Enviando al cliente " + client.getRemoteAddress() + " turno no activo");
                            }

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(jugadores.get(client).getEnviar_primer_imagen()){
                            jugadores.get(client).setEnviar_primer_imagen(Boolean.FALSE);

                            // TODO : Enviar tipo de mensaje e imagen AL OPONENTE

                            if(jugadores.get(client).getEs_jugador_1()){ // Se lo envia al jugador #2
                                juegos_pareja.get(jugadores.get(client).getId_juego()).getJugador_2().
                                        write(ByteBuffer.wrap((tipo_mensaje.get(8) +
                                                jugadores.get(client).getImagen_a_voltear()).getBytes()));

                                System.out.println("Informando al oponente " + juegos_pareja.get(jugadores.get(client).getId_juego()).getJugador_2().getRemoteAddress() + " primer movimiento");
                            }else{ // Se lo envia al jugador #1
                                juegos_pareja.get(jugadores.get(client).getId_juego()).getJugador_1().
                                        write(ByteBuffer.wrap((tipo_mensaje.get(8) +
                                                jugadores.get(client).getImagen_a_voltear()).getBytes()));

                                System.out.println("Informando al oponente " + juegos_pareja.get(jugadores.get(client).getId_juego()).getJugador_1().getRemoteAddress() + " primer movimiento");
                            }

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(jugadores.get(client).getEnviar_segunda_imagen()){
                            jugadores.get(client).setEnviar_primer_imagen(Boolean.FALSE);
                            jugadores.get(client).setEnviar_segunda_imagen(Boolean.FALSE);

                            // TODO : Enviar tipo de mensaje e imagen AL OPONENTE

                            if(jugadores.get(client).getEs_jugador_1()){ // Se lo envia al jugador #2
                                juegos_pareja.get(jugadores.get(client).getId_juego()).getJugador_2().
                                        write(ByteBuffer.wrap((tipo_mensaje.get(9) +
                                                jugadores.get(client).getImagen_a_voltear()).getBytes()));

                                System.out.println("Informando al oponente " + juegos_pareja.get(jugadores.get(client).getId_juego()).getJugador_2().getRemoteAddress() + " segundo movimiento");
                            }else{ // Se lo envia al jugador #1
                                juegos_pareja.get(jugadores.get(client).getId_juego()).getJugador_1().
                                        write(ByteBuffer.wrap((tipo_mensaje.get(9) +
                                                jugadores.get(client).getImagen_a_voltear()).getBytes()));

                                System.out.println("Informando al oponente " + juegos_pareja.get(jugadores.get(client).getId_juego()).getJugador_1().getRemoteAddress() + " segundo movimiento");
                            }

                            key.interestOps(SelectionKey.OP_READ);

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