package view;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public JPanel panel;
    public JLabel lbl_name, lbl_type;
    public JButton btn_ok;
    public JTextField txt_nombre;
    public JComboBox combo;

    public Login(String puertoJugador) {
        setSize(200,170);
        setTitle("Jugador: " + puertoJugador);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    public void initComponents(){
        createPanel();
        createLabels();
        createTextField();
        createButtons();
        createComboBox();
    }

    public void createPanel(){
        panel = new JPanel();
        panel.setLayout(null); // custom edit ok
        panel.setBackground(new Color(226, 240, 203));
        this.getContentPane().add(panel);
    }

    public void createLabels() {
        lbl_name = new JLabel();
        lbl_name.setBounds(10, 10, 70, 30);
        lbl_name.setOpaque(true);
        lbl_name.setForeground(Color.white);
        lbl_name.setBackground(new Color(165, 203, 175));
        lbl_name.setText("Nombre: ");
        lbl_name.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_name.setFont(new Font("arial", 1, 10));
        panel.add(lbl_name);

        lbl_type = new JLabel();
        lbl_type.setBounds(10, 50, 70, 30);
        lbl_type.setOpaque(true);
        lbl_type.setForeground(Color.white);
        lbl_type.setBackground(new Color(165, 203, 175));
        lbl_type.setText("Modo: ");
        lbl_type.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_type.setFont(new Font("arial", 1, 10));
        panel.add(lbl_type);
    }

    public void createTextField(){
        txt_nombre = new JTextField();
        txt_nombre.setBounds(90, 10, 100, 30);
        txt_nombre.setOpaque(true);
        txt_nombre.setForeground(Color.black);
        txt_nombre.setBackground(Color.white);
        txt_nombre.setHorizontalAlignment(SwingConstants.CENTER);
        txt_nombre.setFont(new Font("arial", 1, 10));
        panel.add(txt_nombre);
    }

    public void createButtons(){
        btn_ok = new JButton();
        btn_ok.setBounds(10,90,180,40);
        btn_ok.setBackground(new Color(130, 202, 32));
        btn_ok.setForeground(new Color(130, 202, 32));
        btn_ok.setText("Comenzar");
        btn_ok.setFont(new Font("Calibri", 1, 12));
        panel.add(btn_ok);
    }

    public void createComboBox(){
        combo = new JComboBox<String>();
        combo.setBounds(90,50,100,30);
        combo.addItem("Pareja");
        combo.addItem("Solitario");

        panel.add(combo);
    }
}
