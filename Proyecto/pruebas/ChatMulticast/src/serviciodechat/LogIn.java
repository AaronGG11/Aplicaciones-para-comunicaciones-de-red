

package serviciodechat;

import Interfaz.PanelFondo;
import Interfaz.TextPrompt;
import Interfaz.UIFunctions;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
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
    
    public String getMoviePath(){
        return movie_path;
    }
    
    public String getMovieName(){
        return movie_name;
    }
    
    public String getMovieDescription(){
        return movie_description;
    }
    
    public String getOperationType(){
        return operation_type;
    }
    
    public String getMovieType(){
        return movie_type;
    }
    
    public String getMovieCode(){
        return movie_code;
    }

    private String nombre = null;
    private String operation_type = null;
    private String movie_path = null;
    private String movie_name = null;
    private String movie_description = null;
    private String movie_type = null;
    private String movie_code = null;
    
    
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
        placeholder("Write your name", tfnombre);
        placeholder("Write the movie path", jtf_archivo);
        placeholder("Write the movie name", jtf_movie_name);
        placeholder("Private code", jtf_code);
        
        
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass()
                        .getResource("/Interfaz/icono.png"));
        setIconImage(icon);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbingresar = new javax.swing.JButton();
        jcbaction = new javax.swing.JComboBox<>();
        jlaction = new javax.swing.JLabel();
        tfnombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jtf_archivo = new javax.swing.JTextField();
        jl_title = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jlshowmovie = new javax.swing.JLabel();
        jl_sharemovie = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jl_select_share = new javax.swing.JLabel();
        jl_select_show = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jl_movie_name = new javax.swing.JLabel();
        jtf_movie_name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jta_descripcion = new javax.swing.JTextArea();
        jl_visility = new javax.swing.JLabel();
        jcb_visibility = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jtf_code = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(java.awt.Color.black);
        setForeground(java.awt.Color.white);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(49, 49, 49));

        jbingresar.setBackground(new java.awt.Color(2, 13, 21));
        jbingresar.setForeground(java.awt.Color.white);
        jbingresar.setText("NEXT");
        jbingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbingresarActionPerformed(evt);
            }
        });

        jcbaction.setBackground(java.awt.Color.gray);
        jcbaction.setForeground(java.awt.Color.white);
        jcbaction.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SHOW MOVIE", "SHARE MOVIE" }));

        jlaction.setForeground(java.awt.Color.white);
        jlaction.setText("ACTION:");

        tfnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfnombreActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(1, 1, 1));
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("USERNAME:");

        jtf_archivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtf_archivoActionPerformed(evt);
            }
        });

        jl_title.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jl_title.setForeground(java.awt.Color.white);
        jl_title.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jl_title.setText("MOVIE PEERS - ADI");

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setPreferredSize(new java.awt.Dimension(5, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        jButton1.setBackground(new java.awt.Color(2, 13, 21));
        jButton1.setForeground(java.awt.Color.white);
        jButton1.setText("FIND");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jlshowmovie.setForeground(java.awt.Color.white);
        jlshowmovie.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlshowmovie.setText("SHOW MOVIE");

        jl_sharemovie.setForeground(java.awt.Color.white);
        jl_sharemovie.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_sharemovie.setText("SHARE MOVIE");

        jPanel3.setBackground(java.awt.Color.white);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        jl_select_share.setForeground(java.awt.Color.white);
        jl_select_share.setText("SELECT MOVIE:");

        jl_select_show.setForeground(java.awt.Color.white);
        jl_select_show.setText("SELECT MOVIE:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        jl_movie_name.setForeground(java.awt.Color.white);
        jl_movie_name.setText("MOVIE'S NAME:");

        jtf_movie_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtf_movie_nameActionPerformed(evt);
            }
        });

        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("DESCRIPTION:");

        jta_descripcion.setColumns(20);
        jta_descripcion.setRows(5);
        jScrollPane1.setViewportView(jta_descripcion);

        jl_visility.setForeground(java.awt.Color.white);
        jl_visility.setText("VISIBILITY:");

        jcb_visibility.setBackground(java.awt.Color.gray);
        jcb_visibility.setForeground(java.awt.Color.white);
        jcb_visibility.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PUBLIC", "PRIVATE" }));
        jcb_visibility.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_visibilityActionPerformed(evt);
            }
        });

        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("JOIN UP:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jl_title, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(jlaction)
                    .addComponent(tfnombre)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcbaction, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbingresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlshowmovie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtf_code)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jl_select_show, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jl_sharemovie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtf_archivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jl_movie_name, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jl_select_share, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jl_visility, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jtf_movie_name)
                            .addComponent(jcb_visibility, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_title)
                            .addComponent(jlshowmovie)
                            .addComponent(jl_sharemovie))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlaction)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbingresar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtf_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_select_show)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jl_select_share)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtf_archivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_movie_name)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtf_movie_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jl_visility)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcb_visibility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfnombreActionPerformed

    private void jtf_archivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtf_archivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtf_archivoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        String file_name = file.getAbsolutePath();
        
        jtf_archivo.setText(file_name);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbingresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbingresarActionPerformed

    private void jtf_movie_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtf_movie_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtf_movie_nameActionPerformed

    private void jcb_visibilityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_visibilityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_visibilityActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbingresar;
    private javax.swing.JComboBox<String> jcb_visibility;
    private javax.swing.JComboBox<String> jcbaction;
    private javax.swing.JLabel jl_movie_name;
    private javax.swing.JLabel jl_select_share;
    private javax.swing.JLabel jl_select_show;
    private javax.swing.JLabel jl_sharemovie;
    private javax.swing.JLabel jl_title;
    private javax.swing.JLabel jl_visility;
    private javax.swing.JLabel jlaction;
    private javax.swing.JLabel jlshowmovie;
    private javax.swing.JTextArea jta_descripcion;
    private javax.swing.JTextField jtf_archivo;
    private javax.swing.JTextField jtf_code;
    private javax.swing.JTextField jtf_movie_name;
    private javax.swing.JTextField tfnombre;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if(ae.getSource().equals(jbingresar))
        {
            if((tfnombre.getText().length() > 0) && (jcbaction.getSelectedItem().toString() != null)){
                nombre = tfnombre.getText().trim();
                operation_type = jcbaction.getSelectedItem().toString().trim();
                
                if(operation_type.equals("SHARE MOVIE")){
                    movie_name = jtf_movie_name.getText().trim();
                    movie_path = jtf_archivo.getText().trim();
                    movie_description = jta_descripcion.getText().trim();
                    movie_type = jcb_visibility.getSelectedItem().toString().trim();   
                }
                
                if(operation_type.equals("SHOW MOVIE")){
                    movie_code = jtf_code.getText().trim();
                }
                
                
                dispose();
            } else{
                UIFunctions.informationMessage("Ingresa un nombre", "Invalido");
            }
        }
    }
}
