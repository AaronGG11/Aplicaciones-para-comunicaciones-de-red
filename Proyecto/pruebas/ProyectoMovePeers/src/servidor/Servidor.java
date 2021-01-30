/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

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
            String options = ":sout=#rtp{sdp=rtp://@239.0.0.1:10000/}";
            JFrame frame = new JFrame();
            EmbeddedMediaPlayerComponent em = new EmbeddedMediaPlayerComponent();
            frame.setBounds(0, 0, 500, 600);
            frame.setVisible(true);
            frame.setContentPane(em);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            em.mediaPlayer().media().play("houston.mp4", options,
                    ":no-sout-rtp-sap",
                    ":no-sout-standard-sap",
                    ":sout-all",
                    ":sout-keep",
                    "rtp-frame-buffer-size=10000000"
            );
            frame.setVisible(false);
            System.out.println("Reproduciendo pelicula");
            /*
            EmbeddedMediaPlayerComponent empc = new EmbeddedMediaPlayerComponent();
            MediaPlayerFactory mpf = new MediaPlayerFactory();
            MediaPlayer mp = mpf.mediaPlayers().newMediaPlayer();

            mp.audio().setVolume(100);
            
            JFrame f = new JFrame("Servidor");
            f.setSize(800, 600);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(empc);
            
            f.setVisible(true);
            
            //AccionesVLC.generarTransmision(mp,"p.mp4",10000);
            //f.setVisible(false);
            */
            Thread.sleep(100);
            
            //AccionesVLC.unirseATransmision(empc,"127.0.0.1", 10000);
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
            
   }  
    
}
