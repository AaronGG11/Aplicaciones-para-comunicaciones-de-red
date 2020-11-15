import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class Paquete {
    private Integer id_paquete;
    private Integer puerto;
    private byte[] datos;
    private Integer total_paquetes;

    public Integer getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(Integer id_paquete) {
        this.id_paquete = id_paquete;
    }

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }

    public Integer getTotal_paquetes() {
        return total_paquetes;
    }

    public void setTotal_paquetes(Integer total_paquetes) {
        this.total_paquetes = total_paquetes;
    }
}
