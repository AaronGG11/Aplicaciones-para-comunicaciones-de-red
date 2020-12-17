package model;

import Utilidades.Archivos;
import Utilidades.Memorama;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
                        //memorama.setEs_mi_turno(Boolean.TRUE);

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
                                memorama.setImagenes_orden(Archivos.obtenerOrdenImagenes(true));
                                memorama.setTiene_orden_imagenes(Boolean.TRUE);

                                System.out.println("Se asigno orden de imagenes");
                                client.write(ByteBuffer.wrap((tipo_mensaje.get(3) + new String("uno")).getBytes()));
                            }
                            if(memorama.getTipo_juego().equals("Pareja")){
                                client.write(ByteBuffer.wrap((tipo_mensaje.get(3) + new String("duo")).getBytes()));
                            }

                            System.out.println("Informando al servidor el modo de juego");
                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(memorama.getSolicitar_inicio()){
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(2).getBytes()));
                            System.out.println("Solicitando iniciar el juego");

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(memorama.getTerminar_juego()){
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(4).getBytes()));
                            System.out.println("Solicitando terminar juego");

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(memorama.getSolicitar_pareja()){
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(7).getBytes()));
                            System.out.println("Solicitando contrincante");

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(memorama.getSolicitar_orden_imagenes()){
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(5).getBytes()));
                            System.out.println("Solicitando orden de imagenes");

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(memorama.getSolicitar_turno()){
                            client.write(ByteBuffer.wrap(tipo_mensaje.get(6).getBytes()));
                            System.out.println("Solicitando turno inicial");

                            key.interestOps(SelectionKey.OP_READ);
                        }

                        if(memorama.getEnviar_primer_imagen()){
                            // TODO : Escribir tipo de mensaje y numero de imagen
                            System.out.println("Imagen a enviar movimiento -> " + memorama.getImagen_a_voltear());
                            client.write(ByteBuffer.wrap((tipo_mensaje.get(8) + memorama.getImagen_a_voltear()).getBytes()));
                            System.out.println("Enviando primer imagen");

                            memorama.setEnviar_primer_imagen(Boolean.FALSE);

                            // Escribir de nuevo en cuanto llegue la bandera de segunda imagen
                            key.interestOps(SelectionKey.OP_WRITE);
                        }

                        if(memorama.getEnviar_segunda_imagen()){
                            // TODO : Escribir tipo de mensaje y numero de imagen
                            System.out.println("Imagen a enviar movimiento -> " + memorama.getImagen_a_voltear());
                            client.write(ByteBuffer.wrap((tipo_mensaje.get(9) + memorama.getImagen_a_voltear()).getBytes()));
                            System.out.println("Enviando segunda imagen");

                            memorama.setEnviar_segunda_imagen(Boolean.FALSE);

                            // DEFINIR TURNO
                            if(memorama.getTipo_juego().equals("Pareja")){
                                if(memorama.getSon_imagenes_iguales()){
                                    if(memorama.getEs_mi_turno()){
                                        // es mi turno y las imagenes fueron iguales
                                        memorama.setEs_mi_turno(Boolean.TRUE);
                                    }else{
                                        // no es mi turno y las imagenes fueron iguales
                                        memorama.setEs_mi_turno(Boolean.FALSE);
                                    }
                                }else{
                                    if(memorama.getEs_mi_turno()){
                                        // es mi turno y las imagenes no fueron iguales
                                        memorama.setEs_mi_turno(Boolean.FALSE);
                                    }else{
                                        // no es mi turno y las imagenes no fueron iguales
                                        memorama.setEs_mi_turno(Boolean.TRUE);
                                    }
                                }
                            }

                            System.out.println("¿Es mi turno? " + memorama.getEs_mi_turno());


                            if (memorama.getEs_mi_turno()){
                                System.out.println("Se fue a escribir desde escritura");
                                key.interestOps(SelectionKey.OP_WRITE);
                            }else{
                                System.out.println("Se fue a leer desde escritura");
                                key.interestOps(SelectionKey.OP_READ);
                            }

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

                                // TODO : Enviar al servidor el tipo de juego
                                memorama.setRecibir_tipo(Boolean.TRUE);

                                key.interestOps(SelectionKey.OP_WRITE);
                            }

                            if(tipo_msg.equals("tip")){
                                System.out.println("El servidor registro el modo de juego");
                                memorama.setRecibir_tipo(Boolean.FALSE);

                                // A LA ESPERA DEL BOTON INCIAR
                                // TODO : REVISAR PARA MODO PAREJA
                                memorama.configurarTablero(images_path.toString());
                                memorama.implementsListener();

                                if(memorama.getTipo_juego().equals("Pareja")){
                                    memorama.setSolicitar_pareja(Boolean.TRUE);
                                }

                                key.interestOps(SelectionKey.OP_WRITE);
                            }

                            if(tipo_msg.equals("ini")){
                                memorama.setSolicitar_inicio(Boolean.FALSE);
                                memorama.getTablero().btn_start.setEnabled(Boolean.FALSE);
                                memorama.getTablero().habilitarBotones();

                                memorama.setHora_inicio(LocalTime.now());

                                System.out.println("Se habilitaron imagenes de memorama");
                                System.out.println("Se desactivo boton de iniciar");
                                System.out.println("Puede comenzar jugar, el servidor registro hora de incio");

                                //while(!memorama.getTerminar_juego()){
                                //    continue;
                                //}

                                if(!memorama.getEs_mi_turno()){
                                    key.interestOps(SelectionKey.OP_READ);
                                }else{
                                    key.interestOps(SelectionKey.OP_WRITE);
                                }
                            }

                            if(tipo_msg.equals("fin")){
                                memorama.setTerminar_juego(Boolean.FALSE);
                                memorama.setHora_fin(LocalTime.now());
                                channel.close();

                                Archivos.eliminarCarpeta(images_path.toString(), ""+memorama.getPuerto());

                                System.out.println("El servidor registro hora de fin");
                                System.out.println("Tiempo de juego: "  +
                                        Duration.between(memorama.getHora_inicio(),memorama.getHora_fin()).toSeconds() + " segundos");
                                System.out.println("Juego terminado, socket cerrado correctamente");

                            }


                            if(tipo_msg.equals("jug")){
                                ByteBuffer puerto = ByteBuffer.allocate(5);
                                channel.read(puerto);
                                String puerto_msg = new String(puerto.array(),0,5);
                                puerto.flip();

                                System.out.println("Contrincante puerto: " + puerto_msg);
                                memorama.setPuerto_contrincante(puerto_msg);

                                // Actualizar tablero lbl de contrincante
                                memorama.getTablero().lbl_player_2.setText(puerto_msg);
                                System.out.println("Actualización de tablero, contrincante");

                                memorama.setSolicitar_pareja(Boolean.FALSE);
                                memorama.setTiene_pareja(Boolean.TRUE);

                                if(memorama.getTipo_juego().equals("Pareja")){
                                    memorama.setSolicitar_orden_imagenes(Boolean.TRUE);
                                }

                                key.interestOps(SelectionKey.OP_WRITE);
                            }

                            if(tipo_msg.equals("ord")){
                                memorama.setSolicitar_orden_imagenes(Boolean.FALSE);

                                ByteBuffer orden = ByteBuffer.allocate(600);
                                int aux = channel.read(orden);
                                String orden_imagenes = new String(orden.array(),0,542);
                                orden_imagenes = orden_imagenes.replace("[","");
                                orden_imagenes = orden_imagenes.replace("]","");
                                orden_imagenes = orden_imagenes.replace(" ","");

                                orden.flip();
                                orden.clear();

                                memorama.setImagenes_orden(Arrays.asList(orden_imagenes.split(",")));
                                System.out.println("Se establecio el orden de imagenes");

                                Thread.sleep(50);

                                memorama.setSolicitar_turno(Boolean.TRUE);

                                key.interestOps(SelectionKey.OP_WRITE);
                            }

                            if(tipo_msg.equals("tur")){
                                memorama.setSolicitar_turno(Boolean.FALSE);

                                ByteBuffer turno = ByteBuffer.allocate(5);
                                channel.read(turno);
                                String turno_msg = new String(turno.array(),0,1);
                                turno.flip();
                                turno.clear();

                                if(turno_msg.equals("1")){
                                    memorama.setEs_mi_turno(Boolean.TRUE);
                                    System.out.println("Cliente toma primer turno");

                                }else{
                                    memorama.setEs_mi_turno(Boolean.FALSE);
                                    System.out.println("Cliente toma segundo turno");
                                }

                                memorama.getTablero().btn_start.setEnabled(Boolean.TRUE);
                                System.out.println("Se habilito botón de inicio");

                                key.interestOps(SelectionKey.OP_WRITE);
                            }

                            if(tipo_msg.equals("mv1")){
                                // Captura imagen que hay que mover
                                ByteBuffer imagen = ByteBuffer.allocate(2);
                                channel.read(imagen);
                                imagen.flip();
                                String imagen_mov = new String(imagen.array(),0,2);
                                imagen.clear();

                                Integer img_aux = Integer.parseInt(imagen_mov);

                                memorama.revisaMovimiento(img_aux, images_path.toString() + memorama.getPuerto() + File.separator);
                                memorama.setEnviar_primer_imagen(Boolean.FALSE);
                                memorama.setEnviar_segunda_imagen(Boolean.FALSE);

                                System.out.println("Se mostro primer imagen volteada por oponente -> " + imagen_mov);
                                key.interestOps(SelectionKey.OP_READ);
                            }

                            if(tipo_msg.equals("mv2")){
                                // Captura imagen que hay que mover
                                ByteBuffer imagen = ByteBuffer.allocate(2);
                                channel.read(imagen);
                                imagen.flip();
                                String imagen_mov = new String(imagen.array(),0,2);
                                imagen.clear();

                                Integer img_aux = Integer.parseInt(imagen_mov);

                                memorama.revisaMovimiento(img_aux, images_path.toString() + memorama.getPuerto() + File.separator);

                                memorama.setEnviar_primer_imagen(Boolean.FALSE);
                                memorama.setEnviar_segunda_imagen(Boolean.FALSE);


                                System.out.println("Se mostro segunda imagen volteada por oponente -> " + imagen_mov);
                                //memorama.setEs_mi_turno(Boolean.TRUE);

                                // DEFINIR TURNO
                                if(memorama.getTipo_juego().equals("Pareja")){
                                    if(memorama.getSon_imagenes_iguales()){
                                        if(memorama.getEs_mi_turno()){
                                            // es mi turno y las imagenes fueron iguales
                                            memorama.setEs_mi_turno(Boolean.TRUE);
                                        }else{
                                            // no es mi turno y las imagenes fueron iguales
                                            memorama.setEs_mi_turno(Boolean.FALSE);
                                        }
                                    }else{
                                        if(memorama.getEs_mi_turno()){
                                            // es mi turno y las imagenes no fueron iguales
                                            memorama.setEs_mi_turno(Boolean.FALSE);
                                        }else{
                                            // no es mi turno y las imagenes no fueron iguales
                                            memorama.setEs_mi_turno(Boolean.TRUE);
                                        }
                                    }
                                }

                                System.out.println("¿Es mi turno? " + memorama.getEs_mi_turno());

                                if(memorama.getEs_mi_turno()){
                                    System.out.println("Se fue a escribir desde lectura");
                                    key.interestOps(SelectionKey.OP_WRITE);
                                }else{
                                    System.out.println("Se fue a leer desde lectura");
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }

                            //if(channel.isOpen()){ key.interestOps(SelectionKey.OP_WRITE);}
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