package Vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class Ventana extends JFrame {

    public JPanel panel;
    public JLabel HilosActivos, NodoAnterior, NodoSiguiente, CarpetaAsociada, etiqueta1, etiqueta2, etiqueta3, etiqueta4;
    public JButton btn_Buscar;
    public JTextField jtf_NombreArchivo;
    public JTextArea jta_Notificaciones;
    public JScrollPane barra_scroll;
    public String idHilo;

    /*
        Constructor principal
     */
    public Ventana() {
        setSize(230, 490);//Establecemos el tamaño de la ventana (Ancho, Alto)
        setTitle("Aplicación con hilos para búsqueda de archivos");//Establecemos titulo de la ventana
        setLocationRelativeTo(null);//Establecemos la ventana en el centro
        setResizable(false);//Establecemos si la ventana puede cambiar de tamaño; False-No puede, True-Si puede

        iniciarComponentes();

        setDefaultCloseOperation(EXIT_ON_CLOSE);//Establecemos la opcion de que se cierre el programa con la cruz
    }

    /*
        Constructor de ventana para hilos
     */
    public Ventana(String nombreHilo) {
        setSize(240, 500);//Establecemos el tamaño de la ventana (Ancho, Alto)
        //setBounds(rango, rango, 550, 860); //Posicion y luego tamaño
        setTitle(nombreHilo);//Establecemos titulo de la ventana
        setLocationRelativeTo(null);
        setResizable(false);//Establecemos si la ventana puede cambiar de tamaño; False-No puede, True-Si puede

        iniciarComponentes();
        panel.setBackground(new Color(184, 218, 186));
        HilosActivos.setBackground(new Color(131, 190, 135));
        NodoAnterior.setBackground(new Color(131, 190, 135));
        NodoSiguiente.setBackground(new Color(131, 190, 135));
        CarpetaAsociada.setBackground(new Color(131, 190, 135));
        etiqueta1.setBackground(new Color(184, 218, 186));
        etiqueta2.setBackground(new Color(184, 218, 186));
        etiqueta3.setBackground(new Color(184, 218, 186));
        etiqueta4.setBackground(new Color(184, 218, 186));
        btn_Buscar.setBackground(new Color(98, 156, 103));        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//Decimos que unicamente se cierra la ventana del hilo, no el programa
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Decimos que unicamente se cierra la ventana del hilo, no el programa
    }    
    
    
    
    private void iniciarComponentes() {
        colocaPanel();
        colocaEtiquetas();
        colocaBotones();
        colocaAreasTexto();
    }

    private void colocaPanel() {
        panel = new JPanel();
        panel.setLayout(null); // Desactivamos el diseño por defecto para modificar a gusto
        panel.setBackground(new Color(184, 218, 186));
        this.getContentPane().add(panel); //Agregamos el panel a la ventana
    }

    private void colocaEtiquetas() {
        etiqueta1 = new JLabel();
        etiqueta1.setBounds(25, 20, 90, 30);
        etiqueta1.setOpaque(true);//Desactivamos el diseño por defecto para modificar a gusto
        etiqueta1.setForeground(Color.BLACK);
        etiqueta1.setBackground(new Color(184, 218, 186));
        etiqueta1.setText("Hilos Activos:");
        etiqueta1.setHorizontalAlignment(SwingConstants.CENTER);//Alineacion horizontal del texto en la etiqueta
        etiqueta1.setFont(new Font("arial", 1, 10));
        panel.add(etiqueta1);

        HilosActivos = new JLabel();
        HilosActivos.setBounds(130, 20, 90, 30);
        HilosActivos.setOpaque(true);//Desactivamos el diseño por defecto para modificar a gusto
        HilosActivos.setForeground(Color.white);
        HilosActivos.setBackground(new Color(184, 218, 186));
        HilosActivos.setText("#hilosactivos");
        HilosActivos.setHorizontalAlignment(SwingConstants.CENTER);//Alineacion horizontal del texto en la etiqueta
        HilosActivos.setFont(new Font("arial", 1, 10));
        panel.add(HilosActivos);

        etiqueta2 = new JLabel();
        etiqueta2.setBounds(25, 60, 90, 30);
        etiqueta2.setOpaque(true);//Desactivamos el diseño por defecto para modificar a gusto
        etiqueta2.setForeground(Color.BLACK);
        etiqueta2.setBackground(new Color(85, 85, 205));
        etiqueta2.setText("Nodo Anterior:");
        etiqueta2.setHorizontalAlignment(SwingConstants.CENTER);//Alineacion horizontal del texto en la etiqueta
        etiqueta2.setFont(new Font("arial", 1, 10));
        panel.add(etiqueta2);

        NodoAnterior = new JLabel();
        NodoAnterior.setBounds(130, 60, 90, 30);
        NodoAnterior.setOpaque(true);//Desactivamos el diseño por defecto para modificar a gusto
        NodoAnterior.setForeground(Color.white);
        NodoAnterior.setBackground(new Color(204, 76, 177));
        NodoAnterior.setText("#nodoAnterior");
        NodoAnterior.setHorizontalAlignment(SwingConstants.CENTER);//Alineacion horizontal del texto en la etiqueta
        NodoAnterior.setFont(new Font("arial", 1, 10));
        panel.add(NodoAnterior);

        etiqueta3 = new JLabel();
        etiqueta3.setBounds(25, 100, 90, 30);
        etiqueta3.setOpaque(true);//Desactivamos el diseño por defecto para modificar a gusto
        etiqueta3.setForeground(Color.BLACK);
        etiqueta3.setBackground(new Color(85, 85, 205));
        etiqueta3.setText("Nodo Siguiente:");
        etiqueta3.setHorizontalAlignment(SwingConstants.CENTER);//Alineacion horizontal del texto en la etiqueta
        etiqueta3.setFont(new Font("arial", 1, 10));
        panel.add(etiqueta3);

        NodoSiguiente = new JLabel();
        NodoSiguiente.setBounds(130, 100, 90, 30);
        NodoSiguiente.setOpaque(true);//Desactivamos el diseño por defecto para modificar a gusto
        NodoSiguiente.setForeground(Color.white);
        NodoSiguiente.setBackground(new Color(85, 85, 205));
        NodoSiguiente.setText("#nodoSiguiente");
        NodoSiguiente.setHorizontalAlignment(SwingConstants.CENTER);//Alineacion horizontal del texto en la etiqueta
        NodoSiguiente.setFont(new Font("arial", 1, 10));
        panel.add(NodoSiguiente);

        etiqueta4 = new JLabel();
        etiqueta4.setBounds(25, 140, 90, 30);
        etiqueta4.setOpaque(true);//Desactivamos el diseño por defecto para modificar a gusto
        etiqueta4.setForeground(Color.BLACK);
        etiqueta4.setBackground(new Color(85, 85, 205));
        etiqueta4.setText("Carpeta:");
        etiqueta4.setHorizontalAlignment(SwingConstants.CENTER);//Alineacion horizontal del texto en la etiqueta
        etiqueta4.setFont(new Font("arial", 1, 10));
        panel.add(etiqueta4);

        CarpetaAsociada = new JLabel();
        CarpetaAsociada.setBounds(130, 140, 90, 30);
        CarpetaAsociada.setOpaque(true);//Desactivamos el diseño por defecto para modificar a gusto
        CarpetaAsociada.setForeground(Color.white);
        CarpetaAsociada.setBackground(new Color(85, 85, 205));
        CarpetaAsociada.setText("#carpetaAsociada");
        CarpetaAsociada.setHorizontalAlignment(SwingConstants.CENTER);//Alineacion horizontal del texto en la etiqueta
        CarpetaAsociada.setFont(new Font("arial", 1, 10));
        panel.add(CarpetaAsociada);
    }

    private void colocaBotones() {
        btn_Buscar = new JButton();
        btn_Buscar.setBounds(130, 180, 90, 30);
        btn_Buscar.setText("Buscar");
        btn_Buscar.setForeground(Color.BLACK);
        btn_Buscar.setFont(new Font("arial", 1, 10));
        btn_Buscar.setBackground(new Color(98, 156, 103));
        panel.add(btn_Buscar);
    }

    private void colocaAreasTexto() {
        jtf_NombreArchivo = new JTextField();
        jtf_NombreArchivo.setBounds(25, 180, 90, 30);
        jtf_NombreArchivo.setFont(new Font("arial", 1, 10));
        jtf_NombreArchivo.setBackground(new Color(255,255,255));
        panel.add(jtf_NombreArchivo);

        jta_Notificaciones = new JTextArea();
        jta_Notificaciones.setBounds(25, 220, 200, 240);
        jta_Notificaciones.setFont(new Font("arial", 0, 10));
        //jta_Notificaciones.append("HOLA");//Agrega mas texto
        jta_Notificaciones.setBackground(new Color(255,255,255));
        panel.add(jta_Notificaciones);

        barra_scroll = new JScrollPane(jta_Notificaciones);
        barra_scroll.setBounds(25, 220, 200, 240);
        barra_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        barra_scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(barra_scroll);
    }

}
