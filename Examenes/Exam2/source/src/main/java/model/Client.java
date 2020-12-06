package model;

import javax.swing.*;
import java.io.File;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client { // Sockets TCP (de flujo) NO BLOQUEANTES
    public static void main(String[] args) {
        // Variables
        String host="127.0.0.1";
        Integer puerto = 9000;
        InetSocketAddress dst = new InetSocketAddress(host, puerto);

        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("customers");
        images_path.append(File.separator);

        try{
            // Iniciar programa
            String[] list = {"Pareja", "Solitario"};
            JComboBox jcb = new JComboBox(list);
            JOptionPane.showMessageDialog( null, jcb, "Modo de juego ", JOptionPane.QUESTION_MESSAGE);

            SocketChannel cliente = SocketChannel.open();
            cliente.configureBlocking(false);
            Selector selector = Selector.open();
            cliente.register(selector, SelectionKey.OP_CONNECT);
            cliente.connect(dst);

            while(true){
                selector.select(); // Esperar a tener un evento
                Iterator<SelectionKey> iterador = selector.selectedKeys().iterator();

                while(iterador.hasNext()){
                    SelectionKey clave = (SelectionKey)iterador.next();
                    iterador.remove();

                    if(clave.isConnectable()){

                    }

                    if(clave.isReadable()){

                    }

                    if(clave.isWritable()){

                    }
                }



            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
