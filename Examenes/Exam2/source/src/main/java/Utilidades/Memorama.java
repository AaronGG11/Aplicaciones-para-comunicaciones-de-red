package Utilidades;
import lombok.*;
import view.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.SocketAddress;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Data

public class Memorama implements ActionListener {
    private String path_carpeta;
    private String puerto;
    private String puerto_contrincante;
    private List<String> imagenes; // imagenes no duplicadas ganadas
    private List<String> imagenes_orden; // imagenes en forma de arrelgo, ex. iamgen1.jpg
    private Boolean hay_imagenes;
    private String tipo_juego;
    private Boolean recibir_imagenes;
    private Boolean recibir_movimiento;
    private Boolean terminar_juego;
    private Boolean recibir_tipo;
    private Boolean solicitar_inicio;
    private Boolean esta_jugando;
    private Boolean tiene_pareja;
    private Boolean juego_iniciado;
    private LocalTime hora_inicio;
    private LocalTime hora_fin;
    private Integer pares_ganados;
    private Board tablero;
    private Integer imagen_volteada;
    private Boolean hay_imagen_volteada;
    private Integer botones_libres;
    private Integer imagenes_visibles; // imagenes visibles ganadas por todos
    private Boolean es_mi_turno;
    private Boolean check_buttons[] = new Boolean[41]; // no usar indice cero
    private Boolean es_juego_terminado;

    public Memorama(){
        imagenes = new ArrayList<>();
        hay_imagenes = Boolean.FALSE;
        recibir_imagenes = Boolean.FALSE;
        recibir_movimiento = Boolean.FALSE;
        solicitar_inicio = Boolean.FALSE;
        esta_jugando = Boolean.FALSE;
        recibir_tipo = Boolean.FALSE;
        tiene_pareja = Boolean.FALSE;
        juego_iniciado = Boolean.FALSE;
        hay_imagen_volteada = Boolean.FALSE;
        es_juego_terminado = Boolean.FALSE;
        terminar_juego = Boolean.FALSE;
        es_mi_turno = Boolean.TRUE;
        botones_libres = 40;
        imagenes_visibles = 0;
        imagenes_orden = Archivos.obtenerOrdenImagenes(true); // 1,1,2,2,3,3, ..., 20, 20
        pares_ganados = 0;
    }

    // Antes debera de haber sido establecido el puerto del jugadro, modo de juego
    public void configurarTablero(String path_carpeta){
        this.path_carpeta = path_carpeta + puerto + File.separator;
        tablero = new Board(puerto);
        if(tipo_juego.equals("Solitario")){
            tablero.deshabilitarJugador2();
        }

        tablero.lbl_player_1.setText(puerto);
        tablero.ponerImagenFondo(this.path_carpeta + "fondo.jpg");
        tablero.deshabilitarBotones();
        //tablero.btn_start.setEnabled(Boolean.FALSE);

        tablero.setVisible(Boolean.TRUE);
    }

    public static String obtenerModoJuego(String puerto){
        Object[] botones = {"Pareja", "Solitario"};
        int ventana = JOptionPane.showOptionDialog(null,"Modo de juego",puerto, JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null,botones, botones[0]);

        if(ventana == 0) {return (String)botones[0];} // pareja
        else {return (String)botones[1];} // solitario
    }

    public static Integer enviarZip(){
        Integer resultado=0;

        return resultado;
    }

    public void setTrueCheckButtons() {
        for (int i = 1; i < 41; i++) {
            check_buttons[i] = true;
        }
    }

    public void implementsListener() {
        tablero.btn_11.addActionListener(this);
        tablero.btn_12.addActionListener(this);
        tablero.btn_13.addActionListener(this);
        tablero.btn_14.addActionListener(this);
        tablero.btn_15.addActionListener(this);

        tablero.btn_21.addActionListener(this);
        tablero.btn_22.addActionListener(this);
        tablero.btn_23.addActionListener(this);
        tablero.btn_24.addActionListener(this);
        tablero.btn_25.addActionListener(this);

        tablero.btn_31.addActionListener(this);
        tablero.btn_32.addActionListener(this);
        tablero.btn_33.addActionListener(this);
        tablero.btn_34.addActionListener(this);
        tablero.btn_35.addActionListener(this);

        tablero.btn_41.addActionListener(this);
        tablero.btn_42.addActionListener(this);
        tablero.btn_43.addActionListener(this);
        tablero.btn_44.addActionListener(this);
        tablero.btn_45.addActionListener(this);

        tablero.btn_51.addActionListener(this);
        tablero.btn_52.addActionListener(this);
        tablero.btn_53.addActionListener(this);
        tablero.btn_54.addActionListener(this);
        tablero.btn_55.addActionListener(this);

        tablero.btn_61.addActionListener(this);
        tablero.btn_62.addActionListener(this);
        tablero.btn_63.addActionListener(this);
        tablero.btn_64.addActionListener(this);
        tablero.btn_65.addActionListener(this);

        tablero.btn_71.addActionListener(this);
        tablero.btn_72.addActionListener(this);
        tablero.btn_73.addActionListener(this);
        tablero.btn_74.addActionListener(this);
        tablero.btn_75.addActionListener(this);

        tablero.btn_81.addActionListener(this);
        tablero.btn_82.addActionListener(this);
        tablero.btn_83.addActionListener(this);
        tablero.btn_84.addActionListener(this);
        tablero.btn_85.addActionListener(this);

        tablero.btn_start.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(botones_libres > 0){
            if(es_mi_turno){

                if (e.getSource() == tablero.btn_start) {
                    this.setSolicitar_inicio(Boolean.TRUE);
                }

                if (e.getSource() == tablero.btn_11) {
                    if(check_buttons[1] = true) {
                        revisaMovimiento(1, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_12) {
                    if(check_buttons[2] = true){
                        revisaMovimiento(2, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_13) {
                    if(check_buttons[3] = true){
                        revisaMovimiento(3, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_14) {
                    if(check_buttons[4] = true){
                        revisaMovimiento(4, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_15) {
                    if(check_buttons[5] = true){
                        revisaMovimiento(5, path_carpeta);
                    }
                }

                if (e.getSource() == tablero.btn_21) {
                    if(check_buttons[6] = true){
                        revisaMovimiento(6, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_22) {
                    if(check_buttons[7] = true){
                        revisaMovimiento(7, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_23) {
                    if(check_buttons[8] = true){
                        revisaMovimiento(8, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_24) {
                    if(check_buttons[9] = true){
                        revisaMovimiento(9, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_25) {
                    if(check_buttons[10] = true){
                        revisaMovimiento(10, path_carpeta);
                    }
                }

                if (e.getSource() == tablero.btn_31) {
                    if(check_buttons[11] = true){
                        revisaMovimiento(11, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_32) {
                    if(check_buttons[12] = true){
                        revisaMovimiento(12, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_33) {
                    if(check_buttons[13] = true){
                        revisaMovimiento(13, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_34) {
                    if(check_buttons[14] = true){
                        revisaMovimiento(14, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_35) {
                    if(check_buttons[15] = true){
                        revisaMovimiento(15, path_carpeta);
                    }
                }

                if (e.getSource() == tablero.btn_41) {
                    if(check_buttons[16] = true){
                        revisaMovimiento(16, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_42) {
                    if(check_buttons[17] = true){
                        revisaMovimiento(17, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_43) {
                    if(check_buttons[18] = true){
                        revisaMovimiento(18, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_44) {
                    if(check_buttons[19] = true){
                        revisaMovimiento(19, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_45) {
                    if(check_buttons[20] = true){
                        revisaMovimiento(20, path_carpeta);
                    }
                }

                if (e.getSource() == tablero.btn_51) {
                    if(check_buttons[21] = true){
                        revisaMovimiento(21, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_52) {
                    if(check_buttons[22] = true){
                        revisaMovimiento(22, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_53) {
                    if(check_buttons[23] = true){
                        revisaMovimiento(23, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_54) {
                    if(check_buttons[24] = true){
                        revisaMovimiento(24, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_55) {
                    if(check_buttons[25] = true){
                        revisaMovimiento(25, path_carpeta);
                    }
                }

                if (e.getSource() == tablero.btn_61) {
                    if(check_buttons[26] = true){
                        revisaMovimiento(26, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_62) {
                    if(check_buttons[27] = true){
                        revisaMovimiento(27, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_63) {
                    if(check_buttons[28] = true){
                        revisaMovimiento(28, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_64) {
                    if(check_buttons[29] = true){
                        revisaMovimiento(29, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_65) {
                    if(check_buttons[30] = true){
                        revisaMovimiento(30, path_carpeta);
                    }
                }

                if (e.getSource() == tablero.btn_71) {
                    if(check_buttons[31] = true){
                        revisaMovimiento(31, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_72) {
                    if(check_buttons[32] = true){
                        revisaMovimiento(32, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_73) {
                    if(check_buttons[33] = true){
                        revisaMovimiento(33, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_74) {
                    if(check_buttons[34] = true){
                        revisaMovimiento(34, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_75) {
                    if(check_buttons[35] = true){
                        revisaMovimiento(35, path_carpeta);
                    }
                }

                if (e.getSource() == tablero.btn_81) {
                    if(check_buttons[36] = true){
                        revisaMovimiento(36, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_82) {
                    if(check_buttons[37] = true){
                        revisaMovimiento(37, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_83) {
                    if(check_buttons[38] = true){
                        revisaMovimiento(38, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_84) {
                    if(check_buttons[39] = true){
                        revisaMovimiento(39, path_carpeta);
                    }
                }
                if (e.getSource() == tablero.btn_85) {
                    if(check_buttons[40] = true){
                        revisaMovimiento(40, path_carpeta);
                    }
                }
            }
        }
    }

    public void revisaMovimiento(Integer boton, String path_carpeta) {
        if(!hay_imagen_volteada){
            this.setHay_imagen_volteada(Boolean.TRUE);
            this.setImagen_volteada(boton);

            tablero.establecerImagen(boton, path_carpeta+imagenes_orden.get(boton-1));
        }else{
            tablero.establecerImagen(boton, path_carpeta+imagenes_orden.get(boton-1));

            if(imagenes_orden.get(boton-1).equals(imagenes_orden.get(imagen_volteada-1))){ // Son iguales
                tablero.deshabilitarBoton(imagen_volteada);
                tablero.deshabilitarBoton(boton);
                this.setImagenes_visibles(this.getImagenes_visibles()+2);
                this.setPares_ganados(this.getPares_ganados()+1);
                System.out.println("Se encontro un par de imagenes iguales");
            }else{ // No son iguales
                tablero.establecerImagen(imagen_volteada, path_carpeta+"fondo.jpg");
                tablero.establecerImagen(boton, path_carpeta+"fondo.jpg");
                System.out.println("No se encontro par de imagenes iguales");
            }

            tablero.score_1.setText(""+this.getPares_ganados());
            this.setHay_imagen_volteada(Boolean.FALSE);
            this.setImagen_volteada(null);

            if(this.getPares_ganados()==20){
                this.setTerminar_juego(Boolean.TRUE);
                JOptionPane.showMessageDialog(null, "Ganaste");
                this.setEs_juego_terminado(Boolean.TRUE);
                System.out.println("Tablero eliminado");
                customWait(3000);
                tablero.dispose();
                System.exit( 0 );
            }
        }
    }

    public void customWait(Integer duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException error) {
            System.out.println("Failed to wait: " + error.getMessage());
        }
    }
}
