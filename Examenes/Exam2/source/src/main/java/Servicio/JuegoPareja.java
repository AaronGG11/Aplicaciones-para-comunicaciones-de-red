package Servicio;

import Utilidades.Archivos;
import Utilidades.Memorama;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@AllArgsConstructor
@Data

public class JuegoPareja {
    private Integer id_juego;
    private SocketChannel jugador_1;
    private SocketChannel jugador_2;
    private Boolean es_terminado;
    private Boolean es_inciado;
    private ArrayList<String> parejas_ganadas_jugador_1;
    private ArrayList<String>  parejas_ganadas_jugador_2;
    private Integer parejas_libres;
    private Boolean imagenes[] = new Boolean[41]; // no usar indice cero
    private List<String> imagenes_orden; // imagenes en forma de arrelgo, ex. iamgen1.jpg
    private Boolean iniciar_jugador_1;
    private Boolean iniciar_jugador_2;

    public JuegoPareja(Integer id_juego){
        this.id_juego = id_juego;
        jugador_1 = null;
        jugador_2 = null;
        es_inciado = Boolean.FALSE;
        es_terminado = Boolean.FALSE;
        iniciar_jugador_1 = Boolean.FALSE;
        iniciar_jugador_2 = Boolean.FALSE;
        parejas_ganadas_jugador_1 = new ArrayList<>();
        parejas_ganadas_jugador_2 = new ArrayList<>();
        parejas_libres = 20;
        // TODO : Asignar orden a cada jugador
        imagenes_orden = Archivos.obtenerOrdenImagenes(true); // 1,1,2,2,3,3, ..., 20, 20
        setTrueFreeImages();
    }

    public void setTrueFreeImages() {
        for (int i = 1; i < 41; i++) {
            imagenes[i] = true;
        }
    }

}
