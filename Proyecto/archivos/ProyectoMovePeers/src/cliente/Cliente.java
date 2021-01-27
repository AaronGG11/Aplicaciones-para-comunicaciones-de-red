/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import javax.swing.JFrame;
import servidor.AccionesVLC;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author end user
 */
public class Cliente {
    public static void main(String args[]){
        EmbeddedMediaPlayerComponent empc = new EmbeddedMediaPlayerComponent();
        JFrame f = new JFrame();
        VerPelicula ventana=new VerPelicula();
        //f.setSize(800, 600);
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setContentPane(empc);
            
        //f.setVisible(true);
        //////
        /*
        EmbeddedMediaPlayerComponent player = new EmbeddedMediaPlayerComponent();
        jPanel2.add(player);
        try{
            player.mediaPlayer().media().play("rtsp://127.0.0.1:10000/",
                    "rtsp-frame-buffer-size=10000000");
        }catch (Exception e){
            e.printStackTrace();
        }
        */
        
    }
    
}
