import java.util.ArrayList;

public class Paquete {
    private Integer puerto;
    private Integer id;
    private Integer total;
    private String datos;

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "puerto=" + puerto +
                ", id=" + id +
                ", total=" + total +
                ", datos='" + datos;
    }

    public static String convertirPaquete(Integer puerto, Integer id, Integer total, String datos){
        return "puerto=" + puerto +
                ", id=" + id +
                ", total=" + total +
                ", datos='" + datos;
    }

    public static Integer calcularPaquetes(String mensaje, Integer tamanio){
        byte[] aux = mensaje.getBytes();

        if(aux.length % tamanio != 0)
            return (int) aux.length / tamanio + 1;
        else
            return (int) aux.length / tamanio;
    }

    public static ArrayList calcularSaltos(String mensaje, Integer tamanio){
        ArrayList<Integer> resultado = new ArrayList<>();
        byte[] aux = mensaje.getBytes();

        Integer numero_de_paquetes = calcularPaquetes(mensaje, tamanio);

        for(int i=0; i< aux.length; i+= tamanio){
                resultado.add(i);
        }

        return resultado;
    }

    public static ArrayList crearPaquetes(String mensaje, Integer tamanio){
        ArrayList<byte[]> resultado = new ArrayList<>();




        return resultado;
    }
}
