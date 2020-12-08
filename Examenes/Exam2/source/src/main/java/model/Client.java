package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.channels.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Iterator;


public class Client {
    public static void main(String[] args){
        try{
            String dir="127.0.0.1";
            int pto = 9999;
            ByteBuffer b1=null, b2=null;
            InetSocketAddress dst = new InetSocketAddress(dir,pto);
            SocketChannel cl = SocketChannel.open();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            cl.configureBlocking(false);
            Selector sel = Selector.open();
            cl.register(sel, SelectionKey.OP_CONNECT);
            cl.connect(dst);
            while(true){
                sel.select();
                Iterator<SelectionKey>it = sel.selectedKeys().iterator();
                while(it.hasNext()){
                    SelectionKey k = (SelectionKey)it.next();
                    it.remove();
                    if(k.isConnectable()){
                        SocketChannel ch = (SocketChannel)k.channel();
                        if(ch.isConnectionPending()){
                            System.out.println("Estableciendo conexion con el servidor... espere..");
                            try{
                                ch.finishConnect();
                            }catch(Exception e){
                                e.printStackTrace();
                            }//catch
                            System.out.println("Conexion establecida...\nEscribe texto <Enter> para enviar, SALIR para terminar:");
                        }//if
                        ch.register(sel, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                        continue;
                    }//if
                    if(k.isReadable()){
                        SocketChannel ch = (SocketChannel)k.channel();

                        b1 = ByteBuffer.allocate(500000);
                        b1.clear();

                        byte[] sizeAr = new byte[4];
                        ch.read(ByteBuffer.wrap(sizeAr));

                        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                        byte[] imageAr = new byte[size];
                        ch.read(ByteBuffer.wrap(imageAr));

                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                        System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());

                        Utilidades.crearCarpeta("prueba");

                        // Path de carpeta con imagenes
                        StringBuilder images_path = new StringBuilder();
                        images_path.append("..");
                        images_path.append(File.separator);
                        images_path.append("customers");
                        images_path.append(File.separator);


                        ImageIO.write(image, "jpg", new File(images_path.toString() + "prueba" + File.separator + "imagenx.jpg"));




                        //System.out.println(size);


                        /*
                        b1 = ByteBuffer.allocate(500000);
                        b1.clear();
                        int n = ch.read(b1);
                        b1.flip();
                        System.out.println("Eco  de "+n+" bytes recibido: ");

                         */

                        k.interestOps(SelectionKey.OP_WRITE);
                        continue;
                    } else if(k.isWritable()){
                        k.interestOps(SelectionKey.OP_READ);
                        continue;
                    } //if
                }//while
            }//while
        }catch(Exception e){
            e.printStackTrace();
        }//catch

    }//main
}