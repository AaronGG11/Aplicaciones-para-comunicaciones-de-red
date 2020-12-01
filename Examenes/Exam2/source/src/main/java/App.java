import view.Board;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class App {
    public static void main(String[] args)
    {
        // Path de carpeta de imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("images");
        images_path.append(File.separator);
        images_path.append("fondo.jpg");

        Board tablero = new Board("1234");
        tablero.setVisible(true);

        ImageIcon foto = new ImageIcon(images_path.toString());
        Icon icono = new ImageIcon(foto.getImage().getScaledInstance(tablero.btn_11.getWidth(),tablero.btn_11.getHeight(), Image.SCALE_DEFAULT));
        tablero.btn_11.setIcon(icono);
        tablero.btn_54.setIcon(icono);
        tablero.btn_32.setIcon(icono);
    }
}
