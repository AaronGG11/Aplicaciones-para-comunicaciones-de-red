package Model;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Game {
    private Integer game_id;
    private Integer player_1;
    private Integer player_2;
    private ArrayList<Integer> buttons_player_1;
    private ArrayList<Integer> buttons_player_2;
    private Boolean buttons[] = new Boolean[9];
    private Integer turn;
    private Integer winner;
    private Integer loser;
    private Boolean start;
    private Boolean finish;
    private Integer free_buttons;
    private Semaphore semaphore;


    public Game(Integer game) {
        this.semaphore = new Semaphore(1);
        this.game_id = game;
        this.player_1 = 1;
        this.player_2 = 2;
        this.buttons_player_1 = new ArrayList<>();
        this.buttons_player_2 = new ArrayList<>();
        this.turn = -1;
        this.winner = -1;
        this.loser = -1;
        this.start = Boolean.FALSE;
        this.finish = Boolean.FALSE;
        this.free_buttons = 9;
        initBoard();
    }

    public void initBoard() {
        for(int i=0; i<9; i++){
            buttons[i] = Boolean.TRUE;
        }
    }

    public void blockBoard() {
        for(int i=0; i<9; i++){
            buttons[i] = Boolean.FALSE;
        }
    }

    public void changeTurn(Integer player){
        try {
            semaphore.acquire();

            if(player == player_1) {   turn = player_2;  }
            if(player == player_2) {   turn = player_1;  }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void addButtonToPlayer(Integer player, Integer button_number){
        try {
            semaphore.acquire();

            if((player == player_1) && buttons[button_number]){
                buttons_player_1.add(button_number);
                buttons[button_number] = Boolean.FALSE;
                free_buttons -= 1;
            }
            if((player == player_2) && buttons[button_number]){
                buttons_player_2.add(button_number);
                buttons[button_number] = Boolean.FALSE;
                free_buttons -= 1;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public ArrayList getPlayerButtons(Integer player){
        ArrayList<Integer> aux = new ArrayList<>();

        try {
            semaphore.acquire();

            if(player == player_1){ aux = buttons_player_1; }
            if(player == player_2){ aux = buttons_player_2; }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

        return aux;
    }

    public void assignWinner(Integer player){
        try {
            semaphore.acquire();
            winner = player;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }


    public void assignLoser(Integer player){
        try {
            semaphore.acquire();
            loser = player;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void assignStart(){
        try {
            semaphore.acquire();
            start = Boolean.TRUE;
            turn = 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void assignFinish(){
        try {
            semaphore.acquire();
            finish = Boolean.TRUE;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public Integer getPlayer_1() {
        return player_1;
    }

    public void setPlayer_1(Integer player_1) {
        this.player_1 = player_1;
    }

    public Integer getPlayer_2() {
        return player_2;
    }

    public void setPlayer_2(Integer player_2) {
        this.player_2 = player_2;
    }

    public ArrayList<Integer> getButtons_player_1() {
        return buttons_player_1;
    }

    public void setButtons_player_1(ArrayList<Integer> buttons_player_1) {
        this.buttons_player_1 = buttons_player_1;
    }

    public ArrayList<Integer> getButtons_player_2() {
        return buttons_player_2;
    }

    public void setButtons_player_2(ArrayList<Integer> buttons_player_2) {
        this.buttons_player_2 = buttons_player_2;
    }

    public Boolean[] getButtons() {
        return buttons;
    }

    public void setButtons(Boolean[] buttons) {
        this.buttons = buttons;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getLoser() {
        return loser;
    }

    public void setLoser(Integer loser) {
        this.loser = loser;
    }

    public Boolean getStart() {
        return start;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public Integer getFree_buttons() {
        return free_buttons;
    }

    public void setFree_buttons(Integer free_buttons) {
        this.free_buttons = free_buttons;
    }
}
