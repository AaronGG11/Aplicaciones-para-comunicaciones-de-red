import model.Utilidades;
import view.Board;

import java.io.File;

public class App {
    public static void main(String[] args)
    {
        Board tablero = new Board("fv");
        tablero.setVisible(true);

        tablero.deshabilitarBotones();
        tablero.habilitarBotones();


    }
}
