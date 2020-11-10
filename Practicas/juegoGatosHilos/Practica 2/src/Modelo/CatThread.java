package Modelo;

import Vista.Board;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class CatThread extends Thread implements ActionListener {

    private Game game;
    private Board board;
    private Boolean thread_ready;

    public CatThread(int thread_id, Game game) {
        super("thread" + thread_id);
        this.game = game;
        this.thread_ready = Boolean.FALSE;
    }

    @Override
    public void run() {
        while (true) {
            if (!thread_ready) {
                assignOrderGame();
                createBoard();
                customWait(200);
                updatePlayers();
                implementsListener();
                thread_ready = true;
            } else { // Playing and updating board
                updateBoard();
                if(game.getWinner().equals("")){ // nobody has won
                    updateBoard();
                } else { // somebody has won, but there are still free buttons
                    updateBoard();
                    break;
                }
            }

            if ((game.getFree_buttons() == 0) && game.getWinner().equals("")) {
                JOptionPane.showMessageDialog(null, getName() + ":  tied game");
                break;
            }
        }
        customWait(4000);
        board.dispose();
    }


    public void assignOrderGame() {
        game.assignPlayers(getName());
    }

    public void createBoard() {
        if (game.getPlayer_id_1().equals(getName())) {
            board = new Board(getName(), game.getGame_id());
        }
        if (game.getPlayer_id_2().equals(getName())) {
            board = new Board(getName(), game.getGame_id());
        }
        board.setVisible(true);
    }

    public void customWait(Integer duration) {
        try {
            sleep(duration);
        } catch (InterruptedException error) {
            System.out.println("Failed to wait: " + error.getMessage());
        }
    }


    public void updatePlayers() {
        this.board.player_1.setText(game.getPlayer_id_1());
        this.board.player_2.setText(game.getPlayer_id_2());
        board.turn.setText("Turno: " + game.getTurn());
    }

    public void implementsListener() {
        board.btn1.addActionListener(this);
        board.btn2.addActionListener(this);
        board.btn3.addActionListener(this);
        board.btn4.addActionListener(this);
        board.btn5.addActionListener(this);
        board.btn6.addActionListener(this);
        board.btn7.addActionListener(this);
        board.btn8.addActionListener(this);
        board.btn9.addActionListener(this);
        board.turn.addActionListener(this);
    }


    public void updateBoard() {
        // Actual player is 1, switch to player 2
        if (game.getPlayer_id_1().equals(getName())) {
            ArrayList<Integer> buttons = new ArrayList<>();
            buttons = game.getPlayerButtons2();
            for (Integer id : buttons) {
                modifyBoard(id, 0);
            }
        }

        // Actual player is 2, switch to player 1
        if (game.getPlayer_id_2().equals(getName())) {
            ArrayList<Integer> buttons = new ArrayList<>();
            buttons = game.getPlayerButtons1();
            for (Integer id : buttons) {
                modifyBoard(id, 0);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.getFree_buttons() == 0) {

        } else if (game.getTurn().equals(getName())) {

            game.assignTurn(getName()); // TODO

            if (e.getSource() == board.btn1) {
                if (game.getCheck_buttons()[1] == true) {
                    modifyBoard(1, 1);
                }
            }
            if (e.getSource() == board.btn2) {
                if (game.getCheck_buttons()[2] == true) {
                    modifyBoard(2, 1);
                }
            }
            if (e.getSource() == board.btn3) {
                if (game.getCheck_buttons()[3] == true) {
                    modifyBoard(3, 1);
                }
            }
            if (e.getSource() == board.btn4) {
                if (game.getCheck_buttons()[4] == true) {
                    modifyBoard(4, 1);
                }
            }
            if (e.getSource() == board.btn5) {
                if (game.getCheck_buttons()[5] == true) {
                    modifyBoard(5, 1);
                }
            }
            if (e.getSource() == board.btn6) {
                if (game.getCheck_buttons()[6] == true) {
                    modifyBoard(6, 1);
                }
            }
            if (e.getSource() == board.btn7) {
                if (game.getCheck_buttons()[7] == true) {
                    modifyBoard(7, 1);
                }
            }
            if (e.getSource() == board.btn8) {
                if (game.getCheck_buttons()[8] == true) {
                    modifyBoard(8, 1);
                }
            }
            if (e.getSource() == board.btn9) {
                if (game.getCheck_buttons()[9] == true) {
                    modifyBoard(9, 1);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Wait your turn");
        }
    }

    public void modifyBoard(int button_id, int option) { //
        ImageIcon imageIcon = new ImageIcon();

        if (option == 1) { // this player
            game.getCheck_buttons()[button_id] = false;
            game.addButtonToPlayer(getName(), button_id);
            if (game.getPlayer_id_1().equals(getName())) {
                imageIcon = new ImageIcon("x.png");
            }
            if (game.getPlayer_id_2().equals(getName())) {
                imageIcon = new ImageIcon("o.png");
            }
            findWinner();
        }

        if (option == 0) { // other player
            if (game.getPlayer_id_1().equals(getName())) {
                imageIcon = new ImageIcon("o.png");
            }
            if (game.getPlayer_id_2().equals(getName())) {
                imageIcon = new ImageIcon("x.png");
            }
        }

        if (button_id == 1) {
            board.btn1.setIcon(imageIcon);
        }
        if (button_id == 2) {
            board.btn2.setIcon(imageIcon);
        }
        if (button_id == 3) {
            board.btn3.setIcon(imageIcon);
        }
        if (button_id == 4) {
            board.btn4.setIcon(imageIcon);
        }
        if (button_id == 5) {
            board.btn5.setIcon(imageIcon);
        }
        if (button_id == 6) {
            board.btn6.setIcon(imageIcon);
        }
        if (button_id == 7) {
            board.btn7.setIcon(imageIcon);
        }
        if (button_id == 8) {
            board.btn8.setIcon(imageIcon);
        }
        if (button_id == 9) {
            board.btn9.setIcon(imageIcon);
        }

        board.turn.setText("Turno: " + game.getTurn());
    }


    public void findWinner(){
        ArrayList<Integer> buttons = new ArrayList<>();
        // Player #1
        if(game.getPlayer_id_1().equals(getName())){
            buttons = game.getPlayerButtons1();
        }
        // Player #2
        if(game.getPlayer_id_2().equals(getName())){
            buttons = game.getPlayer_buttons_2();
        }

        /* possible cases where a player can win
         * 1 2 3
         * 4 5 6
         * 7 8 9
         * */

        if(game.getPlayer_id_1().equals(getName())){
            System.out.println(getName() + game.getPlayerButtons1().toString());
        }else{
            System.out.println(getName() + game.getPlayerButtons2().toString());
        }

        if(((buttons.indexOf(1) != -1) && (buttons.indexOf(2) != -1) && (buttons.indexOf(3) != -1)) || // CASE 1: 1,2,3
                ((buttons.indexOf(4) != -1) && (buttons.indexOf(5) != -1) && (buttons.indexOf(6) != -1)) || // CASE 2: 4,5,6
                ((buttons.indexOf(7) != -1) && (buttons.indexOf(8) != -1) && (buttons.indexOf(9) != -1)) || // CASE 3: 7,8,9
                ((buttons.indexOf(1) != -1) && (buttons.indexOf(4) != -1) && (buttons.indexOf(7) != -1)) || // CASE 4: 1,4,7
                ((buttons.indexOf(2) != -1) && (buttons.indexOf(5) != -1) && (buttons.indexOf(8) != -1)) || // CASE 5: 2,5,8
                ((buttons.indexOf(3) != -1) && (buttons.indexOf(6) != -1) && (buttons.indexOf(9) != -1)) || // CASE 6: 3,6,9
                ((buttons.indexOf(1) != -1) && (buttons.indexOf(5) != -1) && (buttons.indexOf(9) != -1)) || // CASE 7: 1,5,9
                ((buttons.indexOf(3) != -1) && (buttons.indexOf(5) != -1) && (buttons.indexOf(7) != -1)))   // CASE 8: 3,5,7
        {
            game.assignWinner(getName());
            JOptionPane.showMessageDialog(null, "Thread --- "+ game.getWinner() +" --- win");
        }












    }
}
