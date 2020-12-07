package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Server { // Sockets TCP (de flujo) NO BLOQUEANTES
    public static void main(String[] args){
        Integer puerto = 9000;
        List<Integer> puertos_activos = new ArrayList<>();

        Boolean bandera_orden = Boolean.TRUE;
        List<String> imagenes_orden = new ArrayList<>();
        imagenes_orden = Utilidades.obtenerOrdenImagenes(bandera_orden);

        // Path de carpeta con imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("images");
        images_path.append(File.separator);

        try{
            ServerSocketChannel s = ServerSocketChannel.open();
            s.configureBlocking(false);
            s.socket().bind(new InetSocketAddress(puerto));

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
                        puertos_activos.add(cl.socket().getPort());
                        System.out.println("Cliente conectado desde " + cl.socket().getInetAddress() + ":"+ cl.socket().getPort());
                        cl.configureBlocking(false);
                        cl.register(sel,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                        continue;
                    }
                    if(k.isReadable()){
                        continue;
                    }else if(k.isWritable()){
                        // Enviar imagenes
                        String imagen_fondo = "fondo.jpg";
                        SocketChannel ch = (SocketChannel)k.channel();

                        BufferedImage image = ImageIO.read(new File(images_path.toString() + imagen_fondo));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write( image, "jpg", baos );
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();
                        ByteBuffer byteBuffer = ByteBuffer.wrap(imageInByte);

                        ch.write(byteBuffer);
                        ch.close();

                        continue;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
