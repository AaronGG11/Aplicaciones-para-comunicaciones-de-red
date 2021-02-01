package serviciodechat;

import Interfaz.PanelFondo;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
/**
 *
 *  @author Aaron Antonio Garcia Gonzalez
 */
public class Cliente extends javax.swing.JFrame implements Runnable, ActionListener, DocumentListener {
    EmbeddedMediaPlayerComponent empc;
    
    public final static String GRUPO = "GRUPO";
    public final int DESCONOCIDO_ID     = 0;
    public final int INICIO_ID          = 1;
    public final int MENSAJE_PRIVADO_ID = 2;
    public final int MENSAJE_PUBLICO_ID = 3;
    public final int FIN_ID             = 4;
    public final int CONFIG_ID          = 5;
    public final int CREATE_GROUP       = 6;
    public final int JOIN_GROUP         = 7;
    
    private String nombre = null;
    private String operation_type = null;
    private String movie_path = null;
    private String movie_name = null;
    private String movie_description = null;
    private String movie_type = null;
    private String movie_code = null;

    
    private String nombreDestino = "";
    private AnalisisDeMensajes am;
    HashMap<String, String> conversaciones = new HashMap<>();//K = Nombre, V = Mensajes
    HashMap<String, JButton> usuarios = new HashMap<>(); //K = nombre , V = jbutton
    HashMap<String, JButton> gruposPrivados = new HashMap<>();
    
    JButton grupo;
    private final PanelFondo contenedor = new PanelFondo("/Interfaz/cuadro-blanco.png");
    
    public Cliente() throws IOException {
        setContentPane(contenedor);
        initComponents();
        init();
        this.setResizable(false); //no permite que sea redimencionable
        this.setLocationRelativeTo(null);//la ventana aparece al centro de la pantalla
        this.setTitle(nombre);
        am = new AnalisisDeMensajes();
        
        Conversacion.setContentType("text/html");
        Conversacion.setEditable(false);
        
        initVideo();
        
         addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                try {
                    close();
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
            }
        });
    }
    
    private void close() throws IOException{
        if (JOptionPane.showConfirmDialog(rootPane, "¿Deseas salir?",
                "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            AdministradorDeOperaciones.getInstance().salirSesion(nombre);
            System.exit(0);
        }
    }
    private void iniciarChat() throws IOException
    {
        LogIn login = new LogIn(new javax.swing.JFrame(), true);
        login.setVisible(true);
    
        nombre = login.getNombre();
        operation_type = login.getOperationType();
        movie_path = login.getMoviePath();
        movie_name = login.getMovieName();
        movie_description = login.getMovieDescription();
        movie_type = login.getMovieType();
        movie_code = login.getMovieCode();
        
        System.out.println("nombre: " + nombre);
        System.out.println("tipo de operacion: " + operation_type);
        System.out.println("path: " + movie_path);
        System.out.println("nombre de peli: " + movie_name);
        System.out.println("descripcion de peli: " + movie_description);
        System.out.println("tipo de peli: " + movie_type);
        System.out.println("codigo de peli: " + movie_code);
        
        if((nombre == null) && (operation_type == null))
        {
            if(operation_type.equals("SHOW MOVIE")){
                if(movie_code == null){
                    System.exit(0);
                }
            } else if(operation_type.equals("SHARE MOVIE")){
                if((movie_path == null) && (movie_name == null) && (movie_type  == null) && (movie_description == null)){
                   System.exit(0); 
                }
            }
            
            System.exit(0);
        }
        else
        {
            nombre.trim();
            AdministradorDeOperaciones.getInstance().iniciarSesion(nombre);
        }
    }

    private void init() throws IOException
    {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass()
                        .getResource("/Interfaz/icono.png"));
        setIconImage(icon);
        iniciarChat();
        Enviar.addActionListener(this);
        Enviar.setEnabled(false);
        
        Create.addActionListener(this);
        Join.addActionListener(this);
        
        Texto.getDocument().addDocumentListener(this);
        grupo = new JButton("GRUPO");
        grupo.addActionListener(this);
        conversaciones.put(GRUPO, "");
        usuarios.put(GRUPO, grupo);
        actualizarBotones();
    }
    
    private void initVideo(){
        empc = new EmbeddedMediaPlayerComponent();
        setLayout(new BorderLayout());
        Video.add(empc, BorderLayout.CENTER); 
        
       
        try{
            empc.mediaPlayer().media().play("rtsp://127.0.0.1:10000/",
                    "rtsp-frame-buffer-size=10000000");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void mostrarMensaje(Mensaje mensaje)
    {
        System.out.print("ID: " + mensaje.getId() + "\n");
        if(mensaje.getNombreOrigen() != null)
        {
            System.out.print("Origen: " + mensaje.getNombreOrigen() + "\n");
        }
        
        if(mensaje.getNombreDestino() != null)
        {
            System.out.print("Destino: " + mensaje.getNombreDestino() + "\n");
        }
        
        if(mensaje.getMensaje() != null)
        {
            System.out.print("Mensaje: " + mensaje.getMensaje() + "\n");
        }
    }
    
    private void actualizar() throws IOException
    {
        Mensaje mensaje = AdministradorDeOperaciones.getInstance().recibe();
        mostrarMensaje(mensaje); 
        if (mensaje.getNombreOrigen() != null) {
            switch (mensaje.getId()) {
                case INICIO_ID:
                    if (!mensaje.getNombreOrigen().equals(nombre)) {
                        iniciarSesion(mensaje.getNombreOrigen());
                    }
                    break;
                case FIN_ID:
                    finSesion(mensaje.getNombreOrigen());
                    break;
                case MENSAJE_PRIVADO_ID:
                    if (!mensaje.getNombreOrigen().equals(nombre)) 
                    {
                        if (mensaje.getNombreDestino().equals(nombre)) //si es para este cliente
                        {
                            visualizarMensajePrivado(mensaje);
                        }
                    }
                    break;
                case MENSAJE_PUBLICO_ID:

                    if (mensaje.getNombreOrigen().equals(nombre)) {
                        mensaje.setNombreOrigen("Tú");
                    }
                    visualizarMensajePublico(mensaje);
                    break;
                case CREATE_GROUP :
                    break;
                case JOIN_GROUP : 
                    break;
                default:

            }
        }

    }
    
    private void visualizarMensajePublico(Mensaje mensaje)
    {
        String   msj = conversaciones.get(GRUPO) + "<br>" + mensaje.getNombreOrigen() 
                  + ": " + mensaje.getMensaje();
        
        conversaciones.put(GRUPO, msj );
        if(nombreDestino.equals(GRUPO))
        {
            Conversacion.setText(am.formatoAMensaje(msj));
            grupo.setForeground(Color.white);
            grupo.setBackground(new Color(59,89,152));
        }else
        {
            grupo.setForeground(Color.white);
            grupo.setBackground(new Color(234,161,27));
        }
    }
    
    private void visualizarMensajePrivado(Mensaje mensaje)
    {
        conversaciones.put(mensaje.getNombreOrigen(), mensaje.getMensaje() );
        if(nombreDestino.equals(mensaje.getNombreOrigen()))
        {
            Conversacion.setText(am.formatoAMensaje(mensaje.getMensaje()));
            usuarios.get(mensaje.getNombreOrigen()).setForeground(Color.white);
            usuarios.get(mensaje.getNombreOrigen()).setBackground(new Color(59,89,152));
        }else
        {
            usuarios.get(mensaje.getNombreOrigen()).setForeground(Color.white);
            usuarios.get(mensaje.getNombreOrigen()).setBackground(new Color(234,161,27));
        }
    }        
        
    private void finSesion(String usuario)
    {
        conversaciones.remove(usuario);
        usuarios.remove(usuario);
        actualizarBotones();
        if(usuario.equals(nombreDestino))
        {
            nombreDestino = "";
            setTitle(nombre);
        }
        habilitarEnvio();
    }
    
    private void iniciarSesion(String nombreNuevo) throws IOException
    {   
        if (!existiaUsuario(nombreNuevo)) 
        {
            AdministradorDeOperaciones.getInstance().iniciarSesion(nombre);
            actualizarBotones();
        }
    }
    
    private void actualizarBotones()
    {
        JPanel panel = new JPanel(new GridLayout(50,0));
        panel.setBackground(Color.WHITE);
        panel.add(grupo);
        Collection<JButton> usuariosConectados = usuarios.values();
        for (JButton u : usuariosConectados) 
            panel.add(u);
        UsuariosConectados.setViewportView(panel);
        if(usuariosConectados.size() == 1)
            grupo.setEnabled(false);
        else
            grupo.setEnabled(true);   
    }
    
    
    private void actualizarBotonPrivado(String codigo)
    {
        JPanel panel = new JPanel(new GridLayout(50,0));
        panel.setBackground(Color.WHITE);
        panel.add(grupo);
        Collection<JButton> usuariosConectados = usuarios.values();
        for (JButton u : usuariosConectados) 
            panel.add(u);
        
        JButton aux_button = new JButton(codigo);
        aux_button.setEnabled(false);
        
        gruposPrivados.put(codigo, aux_button);
        
        Collection<JButton> private_chats = gruposPrivados.values();
        for( JButton u : private_chats)
            panel.add(u);
        
        
        
        UsuariosConectados.setViewportView(panel);
        if(usuariosConectados.size() == 1)
            grupo.setEnabled(false);
        else
            grupo.setEnabled(true); 
    }
    
    
    private boolean existiaUsuario(String nombre)
    {
        if(conversaciones.get(nombre) == null)
        {
            conversaciones.put(nombre, nombre + " ha iniciado sesión");
            JButton b = new JButton(nombre);
            b.setForeground(Color.white);
            b.setBackground(new Color(234,161,27));
            b.addActionListener(this);
            usuarios.put(nombre, b);
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        Conversacion = new javax.swing.JTextPane();
        UsuariosConectados = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        Texto = new javax.swing.JTextArea();
        Enviar = new javax.swing.JButton();
        Video = new javax.swing.JPanel();
        jtf_code = new javax.swing.JTextField();
        Join = new javax.swing.JButton();
        Create = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(254, 254, 254));

        jScrollPane2.setViewportView(Conversacion);

        UsuariosConectados.setBackground(new java.awt.Color(254, 254, 254));

        Texto.setColumns(20);
        Texto.setRows(5);
        jScrollPane3.setViewportView(Texto);

        Enviar.setText("Enviar");

        Video.setBackground(java.awt.Color.black);
        Video.setLayout(new java.awt.BorderLayout());

        jtf_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtf_codeActionPerformed(evt);
            }
        });

        Join.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        Join.setText("JOIN");

        Create.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        Create.setText("CREATE");
        Create.setPreferredSize(new java.awt.Dimension(60, 28));
        Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Video, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtf_code)
                    .addComponent(UsuariosConectados)
                    .addComponent(Enviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Join, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Create, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Video, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtf_code, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Join)
                            .addComponent(Create, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UsuariosConectados, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Enviar)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CreateActionPerformed

    private void jtf_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtf_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtf_codeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        Cliente c =new Cliente();
        c.setVisible(true);
        Runnable run = c;
        Thread thread = new Thread(c);
        thread.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane Conversacion;
    private javax.swing.JButton Create;
    private javax.swing.JButton Enviar;
    private javax.swing.JButton Join;
    private javax.swing.JTextArea Texto;
    private javax.swing.JScrollPane UsuariosConectados;
    private javax.swing.JPanel Video;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jtf_code;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        while(true)
        {
            try {
                actualizar();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if(ae.getSource().equals(Enviar))
        {
            try {
                
                if(nombreDestino.equals(GRUPO))
                {
                    
                    AdministradorDeOperaciones.getInstance()
                        .mensajeASala(Texto.getText(), nombre);
                }
                else
                {   
                    AdministradorDeOperaciones.getInstance()
                        .mensajePrivado(nombre, nombreDestino, Texto.getText());
                }
                
                Texto.setText("");
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(ae.getSource().equals(Create)){
            actualizarBotonPrivado(jtf_code.getText().trim());
        }
        else
        {
            JButton b = (JButton) ae.getSource();
            b.setForeground(Color.white);
            b.setBackground(new Color(59,89,152));
            nombreDestino = b.getText();
            Conversacion.setText(am.formatoAMensaje(conversaciones.get(nombreDestino)));
            this.setTitle(nombre + " - " + nombreDestino);
            habilitarEnvio();
        }
    }

    private boolean analizarTextoAEnviar(String Mensaje)
    {
        int tam = Mensaje.length(); 
        if( tam > 0)
        {
            char c;
            for(int i = 0; i < tam; i++)
            {
                c = Mensaje.charAt(i);
                if(c != ' ' && c != 10 )
                    return true;
            }
        }
        return false;
    }
            
    private void habilitarEnvio()
    {
        if(analizarTextoAEnviar(Texto.getText()) && nombreDestino.length() > 0)
            Enviar.setEnabled(true);
        else
            Enviar.setEnabled(false);   
    }
    
    @Override
    public void insertUpdate(DocumentEvent de) 
    {
        habilitarEnvio();
    }

    @Override
    public void removeUpdate(DocumentEvent de) 
    {
        habilitarEnvio();
    }

    @Override
    public void changedUpdate(DocumentEvent de) 
    {
        habilitarEnvio();
    }
}
