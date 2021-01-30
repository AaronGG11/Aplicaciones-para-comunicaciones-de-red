/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author end user
 */
public class AccionesVLC {
    public static void unirseATransmision(EmbeddedMediaPlayerComponent mediaPlayerComponent , String ip, int puerto){
        //mediaPlayerComponent.getMediaPlayer().playMedia("rtsp://" + ip + ":" + this.puerto + "/");
        //mediaPlayerComponent.mediaPlayer().media().play("rtsp://" + ip + ":" + puerto + "/");
        mediaPlayerComponent.mediaPlayer().media().play("rtsp://127.0.0.1:10000/");
    }
    
    public static void generarTransmision(MediaPlayer mp, String ubicacion, int puerto){
        String opciones = cadenaRTSP(puerto, "");
        mp.media().play(ubicacion, opciones,
            ":no-sout-rtp-sap",
            ":no-sout-standard-sap",
            ":sout-all",
            ":sout-keep");
    }
    
//    public static void generarTransmision(EmbeddedMediaPlayerComponent mediaPlayerComponent, String ubicacion , int puerto){
//        String opciones = cadenaRTSP(puerto, "");
//        System.out.println(opciones);
//        //:sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100,scodec=none}:duplicate{dst=rtp{sdp=rtsp://:8554/},dst=display} :no-sout-all :sout-keep
//        opciones = ":sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100,scodec=none}:duplicate{dst=rtp{sdp=rtsp://:8554/},dst=display}";
//        mediaPlayerComponent.mediaPlayer().media().play(ubicacion, opciones);
//    }
    
    private static String cadenaRTSP(int puerto, String ip){
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{sdp=rtsp://@");
        sb.append(ip);
        sb.append(':');
        sb.append(puerto);
        sb.append('/');
        sb.append("}");
        return sb.toString();
    }
}
