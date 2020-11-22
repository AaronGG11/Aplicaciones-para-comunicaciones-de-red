package Modelo;

import Vista.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hilos extends Thread {
    
    public Ventana hilo;

    public SeccionCritica _ObjetoCritico;

    /*
        #Constructor del objeto de la clase Hilos.
        Se utilizan tres parametros: 
        #IdHilo: es el ID de cada hilo y se hace llamada de su respectivo constructor de la
          clase 'Thread' la cual heradamos y a traves del metodo 'super()' inicializamos su nombre
          de tal forma que tenga la nomenclatura que queremos, en este caso seria ('hilo'+i).
        #NombreArchivo: cadena de texto que tendra el nombre del archivo que vamos a buscar.
        #objetoCritico: Objeto de la clase SeccionCritica, como el nombre lo indica, sera el objeto
          que contiene todos los elementos por los cuales los hilos competiran por modificar, asi mismo
          se hace el paso por referencia ya que todos los hilos tendran el mismo objetoCritico debido
          a que en el main solamente se inicializa un unico objetoCritico.
     */
    public Hilos(int IdHilo, SeccionCritica objetoCritico) {
        super("hilo" + IdHilo);
        this._ObjetoCritico = objetoCritico;
    }

    /*
        #Metodo Run(): Este metodo se hereda de la clase 'Thread', se tiene que sobreescribir para 
          utilizarlo en este problema, debido a que este metodo indica como se comportaran TODOS
          los hilos u objetos instanciados desde esta claso 'Hilos', es decir, todos los objetos 
          del tipo 'Hilo' al momento de ser ejecutados (invocados con el metodo start en el main)
          se comportaran como lo indica el metodo run() que sobreescribimos.
        - Nota: Como se sobreescribe, antes de la declaracion del metodo se coloca la instruccion '@Override' 
            para que java entienda que este metodo run() sera diferente unicamente para los objetos
            del tipo 'Hilo'.
     */
    @Override
    public void run() {

        //System.out.println(getName()+" > "+"Primera Etapa iniciada - Llenar Lista de Orden de Ejecucion");
        //1) Primera etapa del problema: Llenar la lista y con ello saber el orden de ejecucion de los hilos
        while (!_ObjetoCritico.ListaLlena) {
            try {
                _ObjetoCritico.AlmacenaNombres(getName());
                sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Error al llenar la lista de ejecucion circular: " + e.getMessage());
            }
        }

        //1.1) Creamos Cada Ventana Por Hilo una vez ya terminada la lista de ejecucion
        hilo = new Ventana(getName());
        String anterior = _ObjetoCritico.getNodoAnterior(getName());
        String siguiente = _ObjetoCritico.getNodoSiguiente(getName());
        String archivo = _ObjetoCritico.getHilosVivos(getName());
        hilo.NodoAnterior.setText(anterior);
        hilo.NodoSiguiente.setText(siguiente);
        hilo.HilosActivos.setText(archivo);
        hilo.CarpetaAsociada.setText(getName());
        hilo.setVisible(true);

        ActionListener oyenteAccion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hilo.jta_Notificaciones.append("===== Yo inicio la busqueda del archivo ===== \n");
                _ObjetoCritico.TodosEstanRecorridos = false;
                _ObjetoCritico.AlguienYaEmpezoABuscar = false;
                _ObjetoCritico.ArchivoEncontrado = false;
                _ObjetoCritico.HiloSeleccionado = getName();
                _ObjetoCritico.HiloIniciaBusqueda = getName();
                _ObjetoCritico.NombreArchivo = hilo.jtf_NombreArchivo.getText();
                //JOptionPane.showMessageDialog(null, "Hola soy: " + getName() + " Y voy a buscar el archivo: " + hilo.jtf_NombreArchivo.getText());
            }
        };

        hilo.btn_Buscar.addActionListener(oyenteAccion);

        //2) Segunda etapa del problema: Hacer que cada hilo creado busque el archivo       
        while (_ObjetoCritico.TodosEstanRecorridos == false) {
            try {
                if (_ObjetoCritico.HiloSeleccionado.equals(getName())) {
                    //System.out.println(getName()+" > "+"Hora de buscar papa 7w7");                    
                    _ObjetoCritico.BuscaArchivo(getName(), _ObjetoCritico.NombreArchivo, anterior, siguiente, hilo);
                    //hilo.jta_Notificaciones.append("Me toca trabajar a mi\n");
                    //Aqui hay que actualizar los hilos vivos
                    sleep(1000);
                } else {
                    //System.out.println(getName()+" > "+"No es mi turno me voy a dormir");
                    //hilo.jta_Notificaciones.append("Estoy esperando\n");
                    //Aqui hay que actualizar los hilos vivos
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Error al buscar el archivo: " + e.getMessage());
            }
            //hilo.jta_Notificaciones.append("--- YA PARAMOS DE BUSCAR POR ORDEN DE: "+_ObjetoCritico.HiloIniciaBusqueda+" ---\n");
        }

        //3) Tercera etapa del problema: Si el archivo se encuentra, avisar solo a los hilos anteriores; si el archivo no se encuentra, avisar a todos los hilos anteriores y asegurar que todos se recorran        
        while (_ObjetoCritico.TodosEstanAvisados == false) {
            try {
                if (_ObjetoCritico.HiloAvisador.equals(getName())) {

                    if (_ObjetoCritico.ArchivoEncontrado == true) {
                        System.out.println(getName() + " > Tengo que avisar Exito");
                        _ObjetoCritico.Avisa_Encontrado(getName(), anterior, siguiente, hilo);
                    }

                    if (_ObjetoCritico.ArchivoEncontrado == false) {
                        System.out.println(getName() + " > Tengo que avisar Fracaso");
                        _ObjetoCritico.Avisa_NO_Encontrado(getName(), anterior, siguiente, hilo);
                    }

                    sleep(1000);
                } else {
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Error al buscar el archivo: " + e.getMessage());
            }
        }
        
        System.out.println(getName() + " Terminado Correctamente");        
        
    }

}
