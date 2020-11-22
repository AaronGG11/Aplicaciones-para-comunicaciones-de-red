package Modelo;

import javax.swing.JOptionPane;

public class Principal extends Thread {
    
    /*Metodo Principal Donde Mandamos a Llamar a los hilos para su creacion*/

    public static void main(String args[]) throws InterruptedException {

        boolean Correr_Programa = true;
        do {
            int numero = Integer.parseInt(JOptionPane.showInputDialog("Numero Hilos (Minimo 3 - Maximo 10)"));
            if (numero > 10 || numero < 3) {
                JOptionPane.showMessageDialog(null, "Valor Invalido, los parametros son minimo 3 maximo 10");
                System.exit(1);
            }
            SeccionCritica ObjCritico = new SeccionCritica(numero);
            
            Hilos hilos[] = new Hilos[numero];
            for (int i = 0; i < numero; i++) {
                hilos[i] = new Hilos(i, ObjCritico);
                hilos[i].start();
            }
            for (int i = 0; i < numero; i++) {
                hilos[i].join();
            }
            for (int i = 0; i < numero; i++) {
                hilos[i].hilo.HilosActivos.setText("0");
            }
            System.out.println("\n Ya se terminaron de correr todos los hilos");
            JOptionPane.showMessageDialog(null, "Accion Finalizada","Hilos Finalizados",JOptionPane.INFORMATION_MESSAGE);
            for (int i = 0; i < numero; i++) {
                hilos[i].hilo.dispose();
            }
            int respuestas = JOptionPane.showConfirmDialog(null, "¿Quieres Hacer Otra Busqueda?", "Esta Apunto De Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuestas == JOptionPane.YES_OPTION) {//Reiniciamos la busqueda
                Correr_Programa=true;
            } else if (respuestas == JOptionPane.NO_OPTION) {//Salimos del bucle padre
                Correr_Programa=false;
            }
        } while (Correr_Programa);

    }

}
