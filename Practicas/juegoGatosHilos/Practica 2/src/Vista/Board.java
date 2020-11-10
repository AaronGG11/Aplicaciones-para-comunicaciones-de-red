package Vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JFrame {
    
    public JPanel panel;
    public JLabel player_1, versus, player_2;
    public JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, turn;

    public Board(String nombreHilo, int idpartida) {
        setSize(230,350);
        setTitle("Game: " + idpartida + "  Thread: " + nombreHilo);
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
        player_1 = new JLabel();
        player_1.setBounds(20,20, 70, 30);
        player_1.setOpaque(true);
        player_1.setForeground(Color.white);
        player_1.setBackground(new Color(165, 203, 175));
        player_1.setHorizontalAlignment(SwingConstants.CENTER);
        player_1.setFont(new Font("arial", 1, 12));
        panel.add(player_1);

        versus = new JLabel();
        versus.setBounds(100,20, 30, 30);
        versus.setOpaque(true);
        versus.setForeground(Color.white);
        versus.setBackground(new Color(165, 203, 175));
        versus.setText("VS");
        versus.setHorizontalAlignment(SwingConstants.CENTER);
        versus.setFont(new Font("arial", 1, 10));
        panel.add(versus);

        player_2 = new JLabel();
        player_2.setBounds(140,20, 70, 30);
        player_2.setOpaque(true);
        player_2.setForeground(Color.white);
        player_2.setBackground(new Color(165, 203, 175));
        player_2.setHorizontalAlignment(SwingConstants.CENTER);
        player_2.setFont(new Font("arial", 1, 12));
        panel.add(player_2);
    }
    
    public void createButtons(){
        btn1 = new JButton();
        btn1.setBounds(20,70,60,60);
        btn1.setBackground(new Color(130, 202, 32));
        btn1.setForeground(new Color(130, 202, 32));
        panel.add(btn1);

        btn2 = new JButton();
        btn2.setBounds(85,70,60,60);
        btn2.setBackground(new Color(130, 202, 32));
        btn2.setForeground(new Color(130, 202, 32));
        panel.add(btn2);

        btn3 = new JButton();
        btn3.setBounds(150,70,60,60);
        btn3.setBackground(new Color(130, 202, 32));
        btn3.setForeground(new Color(130, 202, 32));
        panel.add(btn3);

        btn4 = new JButton();
        btn4.setBounds(20,135,60,60);
        btn4.setBackground(new Color(130, 202, 32));
        btn4.setForeground(new Color(130, 202, 32));
        panel.add(btn4);

        btn5 = new JButton();
        btn5.setBounds(85,135,60,60);
        btn5.setBackground(new Color(130, 202, 32));
        btn5.setForeground(new Color(130, 202, 32));
        panel.add(btn5);

        btn6 = new JButton();
        btn6.setBounds(150,135,60,60);
        btn6.setBackground(new Color(130, 202, 32));
        btn6.setForeground(new Color(130, 202, 32));
        panel.add(btn6);

        btn7 = new JButton();
        btn7.setBounds(20,200,60,60);
        btn7.setBackground(new Color(130, 202, 32));
        btn7.setForeground(new Color(130, 202, 32));
        panel.add(btn7);

        btn8 = new JButton();
        btn8.setBounds(85,200,60,60);
        btn8.setBackground(new Color(130, 202, 32));
        btn8.setForeground(new Color(130, 202, 32));
        panel.add(btn8);

        btn9 = new JButton();
        btn9.setBounds(150,200,60,60);
        btn9.setBackground(new Color(130, 202, 32));
        btn9.setForeground(new Color(130, 202, 32));
        panel.add(btn9);

        turn = new JButton();
        turn.setBounds(20,280, 190, 30);
        btn9.setBackground(new Color(130, 202, 32));
        btn9.setForeground(new Color(130, 202, 32));
        panel.add(turn);
   }

}
