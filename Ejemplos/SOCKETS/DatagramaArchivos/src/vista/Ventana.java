package vista;

import javax.swing.*;

import sockets.Envio;

import java.io.File;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author tona
 */
public class Ventana extends javax.swing.JFrame {
    private Envio socketEnvio;
    private File archivos[];

    /**
     * Creates new form Vista
     */
    public Ventana() {
        initComponents();
        myInitComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jListArchivos = new javax.swing.JList<>();
        btnEnviar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnSeleccionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jListArchivos);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        jLabel1.setText("Arrastra o selecciona tus archivos a enviar");

        btnSeleccionar.setText("Abrir Archivos");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        layout.createSequentialGroup().addContainerGap()
                                .addGroup(layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                                        jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380,
                                        Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup().addGap(0, 0,
                                                        Short.MAX_VALUE).addComponent(
                                                        btnSeleccionar).addPreferredGap(
                                                        javax.swing.LayoutStyle
                                                                .ComponentPlacement.RELATED)
                                                        .addComponent(btnEnviar)).addGroup(
                                                layout.createSequentialGroup().addComponent(jLabel1)
                                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addContainerGap(
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(
                                jLabel1).addPreferredGap(
                                javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                                jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231,
                                javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnEnviar).addComponent(btnSeleccionar))
                                .addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarActionPerformed(
            java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        // TODO add your handling code here:
        JFileChooser jf = new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int respuesta = jf.showDialog(this, "Seleccionar");
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            archivos = jf.getSelectedFiles();
        }
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void btnEnviarActionPerformed(
            java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:
        if (archivos != null && archivos.length > 0) {
            for (File f : archivos) {
                try {
                    if (f.isDirectory())
                        socketEnvio.enviarCarpetas(f, "");
                    else
                        socketEnvio.enviarArchivo(f, "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            archivos = null;
        } else {
            System.out.println("No hay archivos que enviar");
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and
        feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Ventana().setVisible(true));
    }

    private void myInitComponents() {
        socketEnvio = new Envio();
        jListArchivos.setTransferHandler(new ListTransferHandler(TransferHandler.COPY, socketEnvio));
        jListArchivos.setDropMode(DropMode.INSERT);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jListArchivos;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
