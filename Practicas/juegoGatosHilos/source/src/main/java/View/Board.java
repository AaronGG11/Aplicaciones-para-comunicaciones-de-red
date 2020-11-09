package View;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {
    public JPanel panel;
    public JLabel Player1, Player2, VSLabel;
    public JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    public Board(Integer game, Integer thread_id, String symbol)
    {
        setSize(230,300);
        setTitle("P:" + game + "  H:" + thread_id + "  S: " + symbol);
        setResizable(false);
        initComponents();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void initComponents(){
        createPanel();
        createLabels();
        createButtons();
    }

    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(null); // custom edit
        panel.setBackground(new Color(226, 240, 203));
        this.getContentPane().add(panel);
    }

    private void createLabels() {
        Player1 = new JLabel();
        Player1.setBounds(20,20, 70, 30);
        Player1.setOpaque(true);
        Player1.setForeground(Color.white);
        Player1.setBackground(new Color(165, 203, 175));
        Player1.setText("Jugador #1");
        Player1.setHorizontalAlignment(SwingConstants.CENTER);
        Player1.setFont(new Font("arial", 1, 8));
        panel.add(Player1);

        VSLabel = new JLabel();
        VSLabel.setBounds(100,20, 30, 30);
        VSLabel.setOpaque(true);
        VSLabel.setForeground(Color.white);
        VSLabel.setBackground(new Color(165, 203, 175));
        VSLabel.setText("VS");
        VSLabel.setHorizontalAlignment(SwingConstants.CENTER);
        VSLabel.setFont(new Font("arial", 1, 8));
        panel.add(VSLabel);

        Player2 = new JLabel();
        Player2.setBounds(140,20, 70, 30);
        Player2.setOpaque(true);
        Player2.setForeground(Color.white);
        Player2.setBackground(new Color(165, 203, 175));
        Player2.setText("Jugador #2");
        Player2.setHorizontalAlignment(SwingConstants.CENTER);
        Player2.setFont(new Font("arial", 1, 8));
        panel.add(Player2);
    }

    public void createButtons(){
        btn1 = new JButton();
        btn1.setBounds(20,70,50,50);
        btn1.setBackground(new Color(130, 202, 32));
        btn1.setForeground(new Color(130, 202, 32));
        panel.add(btn1);

        btn2 = new JButton();
        btn2.setBounds(90,70,50,50);
        btn2.setBackground(new Color(130, 202, 32));
        btn2.setForeground(new Color(130, 202, 32));
        panel.add(btn2);

        btn3 = new JButton();
        btn3.setBounds(160,70,50,50);
        btn3.setBackground(new Color(130, 202, 32));
        btn3.setForeground(new Color(130, 202, 32));
        panel.add(btn3);

        btn4 = new JButton();
        btn4.setBounds(20,140,50,50);
        btn4.setBackground(new Color(130, 202, 32));
        btn4.setForeground(new Color(130, 202, 32));
        panel.add(btn4);

        btn5 = new JButton();
        btn5.setBounds(90,140,50,50);
        btn5.setBackground(new Color(130, 202, 32));
        btn5.setForeground(new Color(130, 202, 32));
        panel.add(btn5);

        btn6 = new JButton();
        btn6.setBounds(160,140,50,50);
        btn6.setBackground(new Color(130, 202, 32));
        btn6.setForeground(new Color(130, 202, 32));
        panel.add(btn6);

        btn7 = new JButton();
        btn7.setBounds(20,210,50,50);
        btn7.setBackground(new Color(130, 202, 32));
        btn7.setForeground(new Color(130, 202, 32));
        panel.add(btn7);

        btn8 = new JButton();
        btn8.setBounds(90,210,50,50);
        btn8.setBackground(new Color(130, 202, 32));
        btn8.setForeground(new Color(130, 202, 32));
        panel.add(btn8);

        btn9 = new JButton();
        btn9.setBounds(160,210,50,50);
        btn9.setBackground(new Color(130, 202, 32));
        btn9.setForeground(new Color(130, 202, 32));
        panel.add(btn9);
    }

    public void setTextToJLabel(Integer player, String text){
        if(player == 1){
            Player1.setText(text);
        }
        if(player == 2){
            Player2.setText(text);
        }
    }
}
