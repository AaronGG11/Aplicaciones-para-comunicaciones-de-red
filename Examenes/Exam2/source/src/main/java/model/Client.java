package model;

import javax.swing.*;

public class Client {
    public static void main(String[] args) {
        String[] list = {"Pareja", "Solitario"};
        JComboBox jcb = new JComboBox(list);
        JOptionPane.showMessageDialog( null, jcb, "Modo de juego ", JOptionPane.QUESTION_MESSAGE);

        String modo_juego = jcb.getSelectedItem().toString();
    }
}
