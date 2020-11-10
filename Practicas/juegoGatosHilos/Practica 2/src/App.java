import Modelo.CatThread;
import Modelo.Game;

import javax.swing.JOptionPane;

public class App {
    public static void main(String args[]) throws InterruptedException {
            Integer number_games = Integer.parseInt(JOptionPane.showInputDialog("Number of games"));

            if (number_games < 1) {
                JOptionPane.showMessageDialog(null, "Value not valid, only positive values");
                System.exit(1);
            }

            Game game[] = new Game[number_games];
            CatThread players[] = new CatThread[number_games * 2];

            for (int i = 0, j = 0; j < (number_games); i += 2, j++) {
                game[j] = new Game(j);
                players[i] = new CatThread(i, game[j]);
                players[i + 1] = new CatThread(i + 1, game[j]);

                players[i].start();
                players[i + 1].start();
            }
            for (int j = 0; j < (number_games*2); j += 2) {
                players[j].join();
                players[j + 1].join();
            }
            JOptionPane.showMessageDialog(null, "Action completed", "Finished threads", JOptionPane.INFORMATION_MESSAGE);
    }

}
