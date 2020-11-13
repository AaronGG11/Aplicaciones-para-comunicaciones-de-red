import java.math.BigDecimal;
import java.util.ArrayList;

public class Utilidades {
    public static String ensambarMensaje(ArrayList datagramas){
        StringBuilder mensaje = new StringBuilder();

        for(Object paquete : datagramas){
            mensaje.append(paquete);
        }

        return mensaje.toString();
    }

    public static ArrayList dividirMensaje(String mensaje, Integer salto){
        ArrayList<String> paquetes = new ArrayList<>();



        return paquetes;
    }

    public static Integer calcularNumeroPaquetes(Integer tamanio, Integer salto){
        Integer numero_paquetes = 0;

        if(tamanio % salto == 0){
            return tamanio/salto;
        }else{
            long iPart = new BigDecimal(tamanio/salto).longValue();
            numero_paquetes = Math.toIntExact(iPart) + 1;
        }

        return  numero_paquetes;
    }
}
