package Utils;

import javax.swing.*;

public class Utils {
    public static Integer obtenerLongitud(){
        return Integer.parseInt(JOptionPane.showInputDialog("Tamaño en bytes de paquetes: "));
    }

    public static Boolean obtenerNigle(){
        Object[] botones = {"Si", "No"};
        Integer ventana = JOptionPane.showOptionDialog(null,"¿Usar algoritmo de Nigle?: ","Configuración", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null,botones, botones[0]);

        if(ventana == 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
