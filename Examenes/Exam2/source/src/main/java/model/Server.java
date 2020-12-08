package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;


public class Server { // Sockets TCP (de flujo) NO BLOQUEANTES
    public static void main(String[] args){
        // Mensajes
        List<String> tipo_mensaje = new ArrayList<>();
        tipo_mensaje.add("imagen");
        tipo_mensaje.add("movimiento");
        tipo_mensaje.add("otro");

        // Path de carpeta con imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("images");
        images_path.append(File.separator);

        // Nombres imagenes
        List<String> nombres_imagenes = new ArrayList<>();
        nombres_imagenes = Utilidades.obtenerNombresImagenes();

        // Control de clientes
        Map<SocketChannel, ArrayList<Object>> clientes = new Hashtable<SocketChannel, ArrayList<Object>>();
        // Llave Socket channel
        // ArrayList[0] -> numero de imagenes enviadas
        // ArrayList[1] ->




        try{
            String EECO="";
            int pto=9999;

            ServerSocketChannel s = ServerSocketChannel.open();
            s.configureBlocking(false);
            s.socket().bind(new InetSocketAddress(pto));
            System.out.println("Esperando clientes...");

            Selector sel = Selector.open();
            s.register(sel, SelectionKey.OP_ACCEPT);

            while(true){
                sel.select();
                Iterator<SelectionKey>it = sel.selectedKeys().iterator();

                while(it.hasNext()){
                    SelectionKey k = (SelectionKey)it.next();
                    it.remove();

                    if(k.isAcceptable()){
                        SocketChannel cl = s.accept();
                        System.out.println("Cliente conectado desde " + cl.socket().getInetAddress() + ":"+ cl.socket().getPort());
                        cl.configureBlocking(false);
                        cl.register(sel,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                        continue;
                    }
                    if(k.isReadable()){
                        try{
                            SocketChannel ch = (SocketChannel)k.channel();
                            ByteBuffer b = ByteBuffer.allocate(2000);
                            b.clear();
                            int n=0;
                            String msj="";
                            n=ch.read(b);
                            b.flip();
                            if(n>0)
                                msj = new String(b.array(),0,n);
                            System.out.println("Mensaje de "+n+" bytes recibido: "+msj);
                            if (msj.equalsIgnoreCase("SALIR")){
                                k.interestOps(SelectionKey.OP_WRITE);
                                ch.close();
                                // k.cancel();
                            }else{
                                EECO="ECO->"+msj;
                                k.interestOps(SelectionKey.OP_WRITE);
                            }//else
                        }catch(IOException io){}
                        continue;
                    }else if(k.isWritable()){
                        try{
                            SocketChannel ch = (SocketChannel)k.channel();

                            BufferedImage image = ImageIO.read(new File(images_path + "fondo.jpg"));
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(image, "jpg", byteArrayOutputStream);

                            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

                            ch.write(ByteBuffer.wrap(size));// tamaño
                            ch.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));//datos
                        }catch(IOException io){}
                        k.interestOps(SelectionKey.OP_READ);
                        continue;
                    }//if
                }//while
            }//while
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}