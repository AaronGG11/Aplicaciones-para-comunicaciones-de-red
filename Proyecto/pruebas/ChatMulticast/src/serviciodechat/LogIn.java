

package serviciodechat;

import Interfaz.PanelFondo;
import Interfaz.TextPrompt;
import Interfaz.UIFunctions;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 *  @author Aaron Antonio Garcia Gonzalez
 */
public class LogIn extends javax.swing.JDialog implements ActionListener{

    public String getNombre() 
    {
        return nombre;
    }

    private String nombre = null;
    private final JPanel contenedor = new JPanel();
    public LogIn(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(contenedor);
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(this);
        this.setTitle("Log In");
        init();
        //en porpiedades DISPOSE ON CLOSE
    }

    private void placeholder(String text, JTextField textField)
    {
        TextPrompt placeholder = new TextPrompt(text, textField);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
    }
    
    private void init()
    {
        jbingresar.addActionListener(this);
        placeholder("Nombre de Usuario", tfnombre);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass()
                        .getResource("/Interfaz/icono.png"));
        setIconImage(icon);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfnombre = new javax.swing.JTextField();
        jbingresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(254, 254, 254));
        setForeground(java.awt.Color.white);

        jbingresar.setText("Ingresar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfnombre)
                    .addComponent(jbingresar, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(tfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbingresar)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbingresar;
    private javax.swing.JTextField tfnombre;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if(ae.getSource().equals(jbingresar))
        {
            if(tfnombre.getText().length() > 0)
            {
                nombre = tfnombre.getText().trim();
                dispose();
            }
            else
            {
                UIFunctions.informationMessage("Ingresa un nombre", "Invalido");
            }
        }
    }
}
