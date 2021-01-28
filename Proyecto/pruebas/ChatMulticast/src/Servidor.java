/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author end user
 */
public class Servidor {
   public static void main(String args[]){
            
        try {
            String options = ":sout=#rtp{sdp=rtsp://127.0.0.1:10000/}";
            JFrame frame = new JFrame();
            EmbeddedMediaPlayerComponent em = new EmbeddedMediaPlayerComponent();
            frame.setBounds(0, 0, 500, 600);
            frame.setVisible(true);
            frame.setContentPane(em);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            em.mediaPlayer().media().play("espalda.mp4", options,
                    ":no-sout-rtp-sap",
                    ":no-sout-standard-sap",
                    ":sout-all",
                    ":sout-keep",
                    "rtsp-frame-buffer-size=10000000"
            );
            frame.setVisible(false);
            System.out.println("Reproduciendo pelicula");

            Thread.sleep(100);
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
            
   }  
    
}
