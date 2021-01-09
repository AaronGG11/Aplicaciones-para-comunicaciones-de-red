package prueba;

import prueba.SocketEnvio;
import prueba.ListTransferHandler;


import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends javax.swing.JFrame{
    private SocketEnvio socketEnvio;
    private static final String HOST = "localhost";
    private static final int PORT = 9911;
    private javax.swing.JButton btnChooser;
    private javax.swing.JList<String> jListFiles;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitle;
    private ArrayList<File> lista;

    public Servidor() {
        initComponents();
        myInitComponents();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        s.setReuseAddress(true);

        System.out.println("Servicio iniciado...");

        java.awt.EventQueue.invokeLater(() -> {
            new Servidor().setVisible(true);
        });
    }


    private void initComponents() {

        labelTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListFiles = new javax.swing.JList<>();
        btnChooser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelTitle.setText("Arrastra y suelta tus archivos");

        jScrollPane1.setViewportView(jListFiles);

        btnChooser.setText("Abrir Archivos");
        btnChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        layout.createSequentialGroup().addContainerGap()
                                .addGroup(layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                        layout.createSequentialGroup().addComponent(labelTitle)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, 380,
                                                Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup().addGap(0, 0,
                                                        Short.MAX_VALUE).addComponent(btnChooser)
                                                        .addGap(4, 4, 4))).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(
                                labelTitle).addPreferredGap(
                                javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                                jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210,
                                javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnChooser).addContainerGap(25, Short.MAX_VALUE)));

        labelTitle.getAccessibleContext().setAccessibleDescription("");

        pack();
    }

    private void btnChooserActionPerformed(java.awt.event.ActionEvent evt) {
        // file chooser que acepta multiples archivos y carpetas
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jf.setMultiSelectionEnabled(true);
        int r = jf.showOpenDialog(this);

        if (r == JFileChooser.APPROVE_OPTION) {
            File archivos[] = jf.getSelectedFiles();
            for (File archivo : archivos) {
                try {
                    // Manda las carpetas recursivamente
                    if (archivo.isDirectory())
                    {
                        socketEnvio.enviarCarpetas(archivo, "");
                    }
                    else {
                        socketEnvio.enviarArchivo(archivo, ""); // Manda un solo archivo
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this,
                            "Error al enviar archivos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(this, "Archivo(s) enviado(s)");
        }
    }

    private void myInitComponents() {
        // Creamos nuestro socket de envio
        socketEnvio = new SocketEnvio(HOST, PORT);
        // Utilizamos drag an drop sobre la jList
        jListFiles.setTransferHandler(new ListTransferHandler(TransferHandler.COPY, socketEnvio));
        // la posiscion en la que se inserte sera la que ocupe en la jList
        jListFiles.setDropMode(DropMode.INSERT);
    }


}
