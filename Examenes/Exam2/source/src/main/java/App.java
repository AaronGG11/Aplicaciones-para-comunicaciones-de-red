import Utilidades.Archivos;
import view.Board;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class App {
    public static void main(String[] args)
    {
        // Path de carpeta con imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("server_resources");
        images_path.append(File.separator);
        images_path.append("images");
        images_path.append(File.separator);
        images_path.append("fondo.jpg");

        Board tablero = new Board("algo");
        tablero.setVisible(true);

        tablero.ponerImagenFondo(images_path.toString());
    }
}
