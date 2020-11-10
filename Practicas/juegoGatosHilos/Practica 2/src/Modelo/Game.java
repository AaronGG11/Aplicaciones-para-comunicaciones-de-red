package Modelo;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Game {
    public final Integer game_id;
    Integer free_buttons;
    Boolean check_buttons[] = new Boolean[10];
    Boolean ready;
    ArrayList<Integer> player_buttons_1;
    ArrayList<Integer> player_buttons_2;
    String player_id_1;
    String player_id_2;
    String turn;
    String winner;
    Semaphore semaphore;


    public Game(int game_id) {
        this.game_id = game_id;
        this.free_buttons = 9;
        this.ready = Boolean.FALSE;
        this.player_id_1 = "";
        this.player_id_2 = "";
        this.winner = "";
        this.turn = "";
        this.player_buttons_1 = new ArrayList<>();
        this.player_buttons_2 = new ArrayList<>();
        setTrueCheckButtons();
        this.semaphore = new Semaphore(1);
    }

    public void setTrueCheckButtons() {
        for (int i = 1; i < 10; i++) {
            check_buttons[i] = true;
        }
    }


    public void assignPlayers(String thread_name) {
        try {
            semaphore.acquire();

            if (!ready) {
                player_id_1 = thread_name;
                turn = thread_name;
                ready = true;
            } else {
                player_id_2 = thread_name;
            }
        } catch (InterruptedException error) {
            System.out.println("Player assignment error: " + error.getMessage());
        } finally {
            semaphore.release();
        }
    }


    public void addButtonToPlayer(String thread_name, Integer button_id) {
        try {
            semaphore.acquire();

            if (player_id_1.equals(thread_name)) {
                player_buttons_1.add(button_id);

                check_buttons[button_id] = Boolean.FALSE;
                free_buttons -= 1;
            }

            if (player_id_2.equals(thread_name)) {
                player_buttons_2.add(button_id);

                check_buttons[button_id] = Boolean.FALSE;
                free_buttons -= 1;
            }
        } catch (InterruptedException error) {
            System.out.println("Failed to assign plays: " + error.getMessage());
        } finally {
            semaphore.release();
        }
    }

    public ArrayList getPlayerButtons1() {
        ArrayList<Integer> buttons = new ArrayList<>();
        try {
            semaphore.acquire();

            for (Integer elemento : player_buttons_1) {
                buttons.add(elemento);
            }
        } catch (InterruptedException error) {
            System.out.println("Error returning buttons 1: " + error.getMessage());
        } finally {
            semaphore.release();
        }

        return buttons;
    }

    public ArrayList getPlayerButtons2() {
        ArrayList<Integer> buttons = new ArrayList<>();
        try {
            semaphore.acquire();

            for (Integer elemento : player_buttons_2) {
                buttons.add(elemento);
            }
        } catch (InterruptedException error) {
            System.out.println("Error returning buttons 2: " + error.getMessage());
        } finally {
            semaphore.release();
        }

        return buttons;
    }
    
    public void assignTurn(String thread_name) {
        try {
            semaphore.acquire();

            if(player_id_1.equals(thread_name)){
                turn = player_id_2;
            }
            
            if(player_id_2.equals(thread_name)){
                turn = player_id_1;
            }
        } catch (InterruptedException error) {
            System.out.println("Failed to assign turn" + error.getMessage());
        } finally {
            semaphore.release();
        }
    }
    
    public void assignWinner(String thread_name) {
        try {
            semaphore.acquire();
            winner = thread_name;
        } catch (InterruptedException error) {
            System.out.println("Failed to assign the winner: " + error.getMessage());
        } finally {
            semaphore.release();
        }
    }   
}
