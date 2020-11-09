package Model;

import View.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Hilo extends Thread implements ActionListener{
    private Game game;
    private Board board;
    private Boolean ready;
    private Integer thread_id;

    public Hilo(Game game, Integer thread_id){
        this.game = game;
        this.ready = Boolean.FALSE;
        this.thread_id = thread_id;
    }

    public void run(){
        while(true){
            if(!ready){ // Initialize components
                game.assignStart(); // Player #1 starts first
                createBoard();
                setPlayers();
                setListener();
                this.ready = Boolean.TRUE;
            }else{ // The game is running
                if((game.getFree_buttons() == 0)&&((game.getWinner() == -1))){ // draw -> empate
                    JOptionPane.showMessageDialog(null, "Hilo " + thread_id + ":  Empate");
                }else if((game.getFree_buttons() == 0 && game.getWinner() != -1)||(game.getFree_buttons() > 0 && game.getWinner() != -1)){ // Someone has won
                    updateBoard();
                }else{ // continue the game

                }
            }
        }
    }

    private void setListener() {
        board.btn1.addActionListener((ActionListener) this);
        board.btn2.addActionListener((ActionListener) this);
        board.btn3.addActionListener((ActionListener) this);
        board.btn4.addActionListener((ActionListener) this);
        board.btn5.addActionListener((ActionListener) this);
        board.btn6.addActionListener((ActionListener) this);
        board.btn7.addActionListener((ActionListener) this);
        board.btn8.addActionListener((ActionListener) this);
        board.btn9.addActionListener((ActionListener) this);
    }

    private void setPlayers() {
        board.setTextToJLabel(game.getPlayer_1(), game.getPlayer_1().toString());
        board.setTextToJLabel(game.getPlayer_2(), game.getPlayer_2().toString());
    }

    private void createBoard() {
        if(thread_id == game.getPlayer_1()){
            board = new Board(game.getGame_id(), game.getPlayer_1(), "X");
        }
        if(thread_id == game.getPlayer_2()){
            board = new Board(game.getGame_id(), game.getPlayer_2(), "O");
        }
        board.setVisible(true);
    }

    private void updateBoard(){ // synchonizing with the other player
        // if the actual player is 1, get changes from player 2
        if(game.getPlayer_1() == thread_id){
            ArrayList<Integer> buttons2 = new ArrayList<>();
            buttons2 = game.getButtons_player_2();

            for(Integer button_id : buttons2){
                modifyBoard(button_id);
            }
        }
    }

    private void modifyBoard(Integer button_id){
        ImageIcon imageIcon = new ImageIcon();

        if(game.getButtons()[button_id]){
            game.getButtons()[button_id] = Boolean.FALSE;
            game.setFree_buttons(game.getFree_buttons() - 1);

            if(game.getPlayer_1() == thread_id){
                imageIcon = new ImageIcon("x.png");
            }
            if(game.getPlayer_2() == thread_id){
                imageIcon = new ImageIcon("o.png");
            }

            if (button_id == 0){    board.btn1.setIcon(imageIcon);  }
            if (button_id == 1){    board.btn2.setIcon(imageIcon);  }
            if (button_id == 2){    board.btn3.setIcon(imageIcon);  }
            if (button_id == 3){    board.btn4.setIcon(imageIcon);  }
            if (button_id == 4){    board.btn5.setIcon(imageIcon);  }
            if (button_id == 5){    board.btn6.setIcon(imageIcon);  }
            if (button_id == 6){    board.btn7.setIcon(imageIcon);  }
            if (button_id == 7){    board.btn8.setIcon(imageIcon);  }
            if (button_id == 8){    board.btn9.setIcon(imageIcon);  }

            checkWin();
        }

    }

    private void checkWin() {
        ArrayList<Integer> btns = new ArrayList<>();
        String message = "Hilo: " + thread_id + "ganaste";

        if(game.getPlayer_1() == thread_id){
            btns = game.getButtons_player_1();
        }

        if(game.getPlayer_1() == thread_id) {
            btns = game.getButtons_player_2();
        }

        if((btns.indexOf(0) != -1 && btns.indexOf(1) != -1 && btns.indexOf(2) != -1)||      // 1st option -> 1,2,3
                (btns.indexOf(3) != -1 && btns.indexOf(4) != -1 && btns.indexOf(5) != -1)|| // 2nd option -> 4,5,6
                (btns.indexOf(6) != -1 && btns.indexOf(7) != -1 && btns.indexOf(8) != -1)|| // 3rd option -> 7,8,9
                (btns.indexOf(0) != -1 && btns.indexOf(3) != -1 && btns.indexOf(6) != -1)|| // 4th option -> 1,4,7
                (btns.indexOf(1) != -1 && btns.indexOf(4) != -1 && btns.indexOf(7) != -1)|| // 5th option -> 2,5,8
                (btns.indexOf(2) != -1 && btns.indexOf(5) != -1 && btns.indexOf(8) != -1)|| // 6th option -> 3,6,9
                (btns.indexOf(0) != -1 && btns.indexOf(4) != -1 && btns.indexOf(8) != -1)|| // 7th option -> 1,5,9
                (btns.indexOf(2) != -1 && btns.indexOf(4) != -1 && btns.indexOf(6) != -1))  // 8th option -> 3,5,7
        {
            game.setWinner(thread_id);
            game.setFinish(Boolean.TRUE);
            JOptionPane.showMessageDialog(null, message);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.getFree_buttons() == 0) {
            System.out.println("Partida > " + game.getGame_id() + "   Finalizada");
        } else if (game.getTurn().equals(thread_id)) {

            game.changeTurn(thread_id);

            System.out.println(game.getTurn());

            if (e.getSource() == board.btn1) {
                if (game.getButtons()[0] == true) {
                    modifyBoard(0);
                }
            }
            if (e.getSource() == board.btn2) {
                if (game.getButtons()[1] == true) {
                    modifyBoard(1);
                }
            }
            if (e.getSource() == board.btn3) {
                if (game.getButtons()[2] == true) {
                    modifyBoard(2);
                }
            }
            if (e.getSource() == board.btn4) {
                if (game.getButtons()[3] == true) {
                    modifyBoard(3);
                }
            }
            if (e.getSource() == board.btn5) {
                if (game.getButtons()[4] == true) {
                    modifyBoard(4);
                }
            }
            if (e.getSource() == board.btn6) {
                if (game.getButtons()[5] == true) {
                    modifyBoard(5);
                }
            }
            if (e.getSource() == board.btn7) {
                if (game.getButtons()[6] == true) {
                    modifyBoard(6);
                }
            }
            if (e.getSource() == board.btn8) {
                if (game.getButtons()[7] == true) {
                    modifyBoard(7);
                }
            }
            if (e.getSource() == board.btn9) {
                if (game.getButtons()[8] == true) {
                    modifyBoard(8);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No es tu turno, jugador " + thread_id);
        }
    }

}
