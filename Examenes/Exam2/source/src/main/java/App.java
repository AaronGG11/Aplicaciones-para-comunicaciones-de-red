import Utilidades.Archivos;
import Utilidades.Memorama;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class App {
    public static void main(String[] args) {

        List<String> lista = Archivos.obtenerOrdenImagenes(false);
        String cadena = String.join(",", lista);
        List<String> ensamblado = Arrays.asList(cadena.split(","));

        System.out.println(lista);
        System.out.println(cadena.length());
        System.out.println(ensamblado);





    }
}
