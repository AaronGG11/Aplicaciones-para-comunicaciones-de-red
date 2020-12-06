package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Board extends JFrame {
    public JPanel panel;
    public JLabel lbl_player_1, lbl_player_2, lbl_vs;
    public JLabel lbl_score, score_1, score_2;
    public JLabel lbl_turn;
    public JButton btn_11, btn_12, btn_13, btn_14, btn_15;
    public JButton btn_21, btn_22, btn_23, btn_24, btn_25;
    public JButton btn_31, btn_32, btn_33, btn_34, btn_35;
    public JButton btn_41, btn_42, btn_43, btn_44, btn_45;
    public JButton btn_51, btn_52, btn_53, btn_54, btn_55;
    public JButton btn_61, btn_62, btn_63, btn_64, btn_65;
    public JButton btn_71, btn_72, btn_73, btn_74, btn_75;
    public JButton btn_81, btn_82, btn_83, btn_84, btn_85;
    public JButton btn_turn, btn_start;

    public Board(String puertoJugador) {
        setSize(590,725);
        setTitle("Jugador: " + puertoJugador);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
    }

    public void initComponents(){
        createPanel();
        createLabels();
        createButtons();
    }

    public void createPanel(){
        panel = new JPanel();
        panel.setLayout(null); // custom edit ok
        panel.setBackground(new Color(226, 240, 203));
        this.getContentPane().add(panel);
    }


    public void createLabels(){
        lbl_player_1 = new JLabel();
        lbl_player_1.setBounds(10,620, 170, 30);
        lbl_player_1.setOpaque(true);
        lbl_player_1.setForeground(Color.white);
        lbl_player_1.setBackground(new Color(165, 203, 175));
        lbl_player_1.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_player_1.setFont(new Font("arial", 1, 10));
        panel.add(lbl_player_1);

        lbl_vs = new JLabel();
        lbl_vs.setBounds(190,620, 90, 30);
        lbl_vs.setOpaque(true);
        lbl_vs.setForeground(Color.white);
        lbl_vs.setBackground(new Color(165, 203, 175));
        lbl_vs.setText("Turno");
        lbl_vs.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_vs.setFont(new Font("arial", 1, 10));
        panel.add(lbl_vs);

        lbl_player_2 = new JLabel();
        lbl_player_2.setBounds(290,620, 170, 30);
        lbl_player_2.setOpaque(true);
        lbl_player_2.setForeground(Color.white);
        lbl_player_2.setBackground(new Color(165, 203, 175));
        lbl_player_2.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_player_2.setFont(new Font("arial", 1, 10));
        panel.add(lbl_player_2);

        lbl_score = new JLabel();
        lbl_score.setBounds(10,660, 80, 30);
        lbl_score.setOpaque(true);
        lbl_score.setForeground(Color.white);
        lbl_score.setBackground(new Color(165, 203, 175));
        lbl_score.setText("Puntuación: ");
        lbl_score.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_score.setFont(new Font("arial", 1, 10));
        panel.add(lbl_score);

        score_1 = new JLabel();
        score_1.setBounds(100,660, 80, 30);
        score_1.setOpaque(true);
        score_1.setForeground(Color.white);
        score_1.setBackground(new Color(165, 203, 175));
        score_1.setText("0");
        score_1.setHorizontalAlignment(SwingConstants.CENTER);
        score_1.setFont(new Font("arial", 1, 10));
        panel.add(score_1);

        /*
        // turn button
        btn_turn = new JButton();
        btn_turn.setBounds(190,660,90,30);
        btn_turn.setBackground(new Color(130, 202, 32));
        btn_turn.setForeground(new Color(130, 202, 32));
        btn_turn.setText("1234");
        panel.add(btn_turn);
         */

        lbl_turn = new JLabel();
        lbl_turn.setBounds(190,660, 90, 30);
        lbl_turn.setOpaque(true);
        lbl_turn.setForeground(Color.white);
        lbl_turn.setBackground(new Color(165, 203, 175));
        lbl_turn.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_turn.setFont(new Font("arial", 1, 10));
        panel.add(lbl_turn);


        lbl_score = new JLabel();
        lbl_score.setBounds(290,660, 80, 30);
        lbl_score.setOpaque(true);
        lbl_score.setForeground(Color.white);
        lbl_score.setBackground(new Color(165, 203, 175));
        lbl_score.setText("Puntuación: ");
        lbl_score.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_score.setFont(new Font("arial", 1, 10));
        panel.add(lbl_score);

        score_2 = new JLabel();
        score_2.setBounds(380,660, 80, 30);
        score_2.setOpaque(true);
        score_2.setForeground(Color.white);
        score_2.setBackground(new Color(165, 203, 175));
        score_2.setText("0");
        score_2.setHorizontalAlignment(SwingConstants.CENTER);
        score_2.setFont(new Font("arial", 1, 10));
        panel.add(score_2);
    }

    public void createButtons(){
        // Boton iniciar
        btn_start = new JButton();
        btn_start.setBounds(470,620,110,70);
        btn_start.setBackground(new Color(130, 202, 32));
        btn_start.setForeground(new Color(130, 202, 32));
        btn_start.setText("Iniciar");
        btn_start.setFont(new Font("Calibri", 1, 15));
        panel.add(btn_start);

        // Fila #1
        btn_11 = new JButton();
        btn_11.setBounds(10,10,110,70);
        btn_11.setBackground(new Color(130, 202, 32));
        btn_11.setForeground(new Color(130, 202, 32));
        panel.add(btn_11);

        btn_12 = new JButton();
        btn_12.setBounds(125,10,110,70);
        btn_12.setBackground(new Color(130, 202, 32));
        btn_12.setForeground(new Color(130, 202, 32));
        panel.add(btn_12);

        btn_13 = new JButton();
        btn_13.setBounds(240,10,110,70);
        btn_13.setBackground(new Color(130, 202, 32));
        btn_13.setForeground(new Color(130, 202, 32));
        panel.add(btn_13);

        btn_14 = new JButton();
        btn_14.setBounds(355,10,110,70);
        btn_14.setBackground(new Color(130, 202, 32));
        btn_14.setForeground(new Color(130, 202, 32));
        panel.add(btn_14);

        btn_15 = new JButton();
        btn_15.setBounds(470,10,110,70);
        btn_15.setBackground(new Color(130, 202, 32));
        btn_15.setForeground(new Color(130, 202, 32));
        panel.add(btn_15);

        // Fila #2
        btn_21 = new JButton();
        btn_21.setBounds(10,85,110,70);
        btn_21.setBackground(new Color(130, 202, 32));
        btn_21.setForeground(new Color(130, 202, 32));
        panel.add(btn_21);

        btn_22 = new JButton();
        btn_22.setBounds(125,85,110,70);
        btn_22.setBackground(new Color(130, 202, 32));
        btn_22.setForeground(new Color(130, 202, 32));
        panel.add(btn_22);

        btn_23 = new JButton();
        btn_23.setBounds(240,85,110,70);
        btn_23.setBackground(new Color(130, 202, 32));
        btn_23.setForeground(new Color(130, 202, 32));
        panel.add(btn_23);

        btn_24 = new JButton();
        btn_24.setBounds(355,85,110,70);
        btn_24.setBackground(new Color(130, 202, 32));
        btn_24.setForeground(new Color(130, 202, 32));
        panel.add(btn_24);

        btn_25 = new JButton();
        btn_25.setBounds(470,85,110,70);
        btn_25.setBackground(new Color(130, 202, 32));
        btn_25.setForeground(new Color(130, 202, 32));
        panel.add(btn_25);

        // Fila #3
        btn_31 = new JButton();
        btn_31.setBounds(10,160,110,70);
        btn_31.setBackground(new Color(130, 202, 32));
        btn_31.setForeground(new Color(130, 202, 32));
        panel.add(btn_31);

        btn_32 = new JButton();
        btn_32.setBounds(125,160,110,70);
        btn_32.setBackground(new Color(130, 202, 32));
        btn_32.setForeground(new Color(130, 202, 32));
        panel.add(btn_32);

        btn_33 = new JButton();
        btn_33.setBounds(240,160,110,70);
        btn_33.setBackground(new Color(130, 202, 32));
        btn_33.setForeground(new Color(130, 202, 32));
        panel.add(btn_33);

        btn_34 = new JButton();
        btn_34.setBounds(355,160,110,70);
        btn_34.setBackground(new Color(130, 202, 32));
        btn_34.setForeground(new Color(130, 202, 32));
        panel.add(btn_34);

        btn_35 = new JButton();
        btn_35.setBounds(470,160,110,70);
        btn_35.setBackground(new Color(130, 202, 32));
        btn_35.setForeground(new Color(130, 202, 32));
        panel.add(btn_35);

        // Fila #4
        btn_41 = new JButton();
        btn_41.setBounds(10,235,110,70);
        btn_41.setBackground(new Color(130, 202, 32));
        btn_41.setForeground(new Color(130, 202, 32));
        panel.add(btn_41);

        btn_42 = new JButton();
        btn_42.setBounds(125,235,110,70);
        btn_42.setBackground(new Color(130, 202, 32));
        btn_42.setForeground(new Color(130, 202, 32));
        panel.add(btn_42);

        btn_43 = new JButton();
        btn_43.setBounds(240,235,110,70);
        btn_43.setBackground(new Color(130, 202, 32));
        btn_43.setForeground(new Color(130, 202, 32));
        panel.add(btn_43);

        btn_44 = new JButton();
        btn_44.setBounds(355,235,110,70);
        btn_44.setBackground(new Color(130, 202, 32));
        btn_44.setForeground(new Color(130, 202, 32));
        panel.add(btn_44);

        btn_45 = new JButton();
        btn_45.setBounds(470,235,110,70);
        btn_45.setBackground(new Color(130, 202, 32));
        btn_45.setForeground(new Color(130, 202, 32));
        panel.add(btn_45);

        // Fila #5
        btn_51 = new JButton();
        btn_51.setBounds(10,310,110,70);
        btn_51.setBackground(new Color(130, 202, 32));
        btn_51.setForeground(new Color(130, 202, 32));
        panel.add(btn_51);

        btn_52 = new JButton();
        btn_52.setBounds(125,310,110,70);
        btn_52.setBackground(new Color(130, 202, 32));
        btn_52.setForeground(new Color(130, 202, 32));
        panel.add(btn_52);

        btn_53 = new JButton();
        btn_53.setBounds(240,310,110,70);
        btn_53.setBackground(new Color(130, 202, 32));
        btn_53.setForeground(new Color(130, 202, 32));
        panel.add(btn_53);

        btn_54 = new JButton();
        btn_54.setBounds(355,310,110,70);
        btn_54.setBackground(new Color(130, 202, 32));
        btn_54.setForeground(new Color(130, 202, 32));
        panel.add(btn_54);

        btn_55 = new JButton();
        btn_55.setBounds(470,310,110,70);
        btn_55.setBackground(new Color(130, 202, 32));
        btn_55.setForeground(new Color(130, 202, 32));
        panel.add(btn_55);

        // Fila #6
        btn_61 = new JButton();
        btn_61.setBounds(10,385,110,70);
        btn_61.setBackground(new Color(130, 202, 32));
        btn_61.setForeground(new Color(130, 202, 32));
        panel.add(btn_61);

        btn_62 = new JButton();
        btn_62.setBounds(125,385,110,70);
        btn_62.setBackground(new Color(130, 202, 32));
        btn_62.setForeground(new Color(130, 202, 32));
        panel.add(btn_62);

        btn_63 = new JButton();
        btn_63.setBounds(240,385,110,70);
        btn_63.setBackground(new Color(130, 202, 32));
        btn_63.setForeground(new Color(130, 202, 32));
        panel.add(btn_63);

        btn_64 = new JButton();
        btn_64.setBounds(355,385,110,70);
        btn_64.setBackground(new Color(130, 202, 32));
        btn_64.setForeground(new Color(130, 202, 32));
        panel.add(btn_64);

        btn_65 = new JButton();
        btn_65.setBounds(470,385,110,70);
        btn_65.setBackground(new Color(130, 202, 32));
        btn_65.setForeground(new Color(130, 202, 32));
        panel.add(btn_65);

        // Fila #7
        btn_71 = new JButton();
        btn_71.setBounds(10,460,110,70);
        btn_71.setBackground(new Color(130, 202, 32));
        btn_71.setForeground(new Color(130, 202, 32));
        panel.add(btn_71);

        btn_72 = new JButton();
        btn_72.setBounds(125,460,110,70);
        btn_72.setBackground(new Color(130, 202, 32));
        btn_72.setForeground(new Color(130, 202, 32));
        panel.add(btn_72);

        btn_73 = new JButton();
        btn_73.setBounds(240,460,110,70);
        btn_73.setBackground(new Color(130, 202, 32));
        btn_73.setForeground(new Color(130, 202, 32));
        panel.add(btn_73);

        btn_74 = new JButton();
        btn_74.setBounds(355,460,110,70);
        btn_74.setBackground(new Color(130, 202, 32));
        btn_74.setForeground(new Color(130, 202, 32));
        panel.add(btn_74);

        btn_75 = new JButton();
        btn_75.setBounds(470,460,110,70);
        btn_75.setBackground(new Color(130, 202, 32));
        btn_75.setForeground(new Color(130, 202, 32));
        panel.add(btn_75);

        // Fila #8
        btn_81 = new JButton();
        btn_81.setBounds(10,535,110,70);
        btn_81.setBackground(new Color(130, 202, 32));
        btn_81.setForeground(new Color(130, 202, 32));
        panel.add(btn_81);

        btn_82 = new JButton();
        btn_82.setBounds(125,535,110,70);
        btn_82.setBackground(new Color(130, 202, 32));
        btn_82.setForeground(new Color(130, 202, 32));
        panel.add(btn_82);

        btn_83 = new JButton();
        btn_83.setBounds(240,535,110,70);
        btn_83.setBackground(new Color(130, 202, 32));
        btn_83.setForeground(new Color(130, 202, 32));
        panel.add(btn_83);

        btn_84 = new JButton();
        btn_84.setBounds(355,535,110,70);
        btn_84.setBackground(new Color(130, 202, 32));
        btn_84.setForeground(new Color(130, 202, 32));
        panel.add(btn_84);

        btn_85 = new JButton();
        btn_85.setBounds(470,535,110,70);
        btn_85.setBackground(new Color(130, 202, 32));
        btn_85.setForeground(new Color(130, 202, 32));
        panel.add(btn_85);
    }
}
