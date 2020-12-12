package Utilidades;

import lombok.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Data

public class Memorama {
    private String puerto;
    private String puerto_contrincante;
    private List<String> imagenes; // imagenes no duplicadas ganadas
    private Boolean hay_imagenes;
    private String tipo_juego;

    public Memorama(){
        imagenes = new ArrayList<>();
        hay_imagenes = Boolean.FALSE;
    }

    public static String obtenerModoJuego(String puerto){
        Object[] possibleValues = { "Pareja", "Solitario"};
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Elije el modo de juego", puerto,
                JOptionPane.QUESTION_MESSAGE, null,
                possibleValues, possibleValues[0]);

        return (String)selectedValue;
    }

}
