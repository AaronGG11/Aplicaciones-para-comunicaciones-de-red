import Model.Game;
import Model.Hilo;
import View.Board;

import javax.swing.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Integer number_games = Integer.parseInt(JOptionPane.showInputDialog("Número de partidas: "));
        Game games[] = new Game[number_games];
        Hilo gamers[]= new Hilo[number_games*2];

        for(Integer i=0, j=0; j<number_games; i+=2, j++){
            games[j] = new Game(j);
            gamers[i] = new Hilo(games[j], 1);
            gamers[i+1] = new Hilo(games[j], 2);

            gamers[i].start();
            gamers[i+1].start();
        }
        for(Integer i=0; i<(number_games*2); i+=2){
            gamers[i].join();
            gamers[i + 1].join();
        }
    }
}
