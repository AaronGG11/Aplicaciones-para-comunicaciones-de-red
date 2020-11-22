package Modelo;

import Vista.Ventana;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SeccionCritica extends Thread {

    Semaphore semaforo;
    boolean AlguienYaEmpezoABuscar, AlguienYaEmpezoAvisar, ArchivoEncontrado, ListaLlena, TodosEstanRecorridos, TodosEstanAvisados;
    int HilosUsados, PosicionRecorrida, MaximoHilos, HilosVivos;
    ArrayList<String> NombreHilos;
    String HiloSeleccionado;
    String HiloIniciaBusqueda;
    String HiloAvisador;
    String HiloReferencia;
    String RespuestaFinal;
    String NombreArchivo;
    Ventana ventanaPrincipal;

    /*
        #Constructor del objeto SeccionCritica
        - Se inicializa el semaforo, la lista de ejecucion asi como variables booleanas y enteras.
        - Se solicita el numero maximo de hilos que pueden estar dentro del ArrayList, esto con el fin
           de tener un mejor control de los hilos que van a competir por la seccion critica del objeto
           que se instancia desde esta clase, el cual sera el objetoCritico del problema.
     */
    public SeccionCritica(int tamanio) {
        semaforo = new Semaphore(1);
        ArchivoEncontrado = false;
        ListaLlena = false;
        TodosEstanRecorridos = false;
        TodosEstanAvisados = false;
        AlguienYaEmpezoABuscar = false;
        AlguienYaEmpezoAvisar = false;
        HilosUsados = 0;
        PosicionRecorrida = 0;
        HilosVivos = 0;
        MaximoHilos = tamanio;
        NombreHilos = new ArrayList<>();
        HiloSeleccionado = "";
        RespuestaFinal = "";
        HiloIniciaBusqueda = "";
        NombreArchivo = "";
        HiloAvisador = "";
        HiloReferencia = "";
    }

    public String getNodoAnterior(String nombreHilo) {
        int indice = 0;
        for (String elemento : NombreHilos) {
            if (elemento.equals(nombreHilo)) {
                //System.out.println("El hilo :" + nombreHilo + " Tiene la posicion: " + indice);
                break;
            }
            indice++;
        }
        //System.out.println("El indice se quedo con el valor de: " + indice);

        String Nodo_Anterior = "";
        if (indice == 0) {
            Nodo_Anterior = NombreHilos.get(MaximoHilos - 1);
        } else {
            Nodo_Anterior = NombreHilos.get(indice - 1);
        }

        return Nodo_Anterior;
    }

    public String getNodoSiguiente(String nombreHilo) {
        int indice = 0;
        for (String elemento : NombreHilos) {
            if (elemento.equals(nombreHilo)) {
                //System.out.println("El hilo :" + nombreHilo + " Tiene la posicion: " + indice);
                break;
            }
            indice++;
        }
        //System.out.println("El indice se quedo con el valor de: " + indice);

        String Nodo_Siguiente = "";
        if (indice == MaximoHilos - 1) {
            Nodo_Siguiente = NombreHilos.get(0);
        } else {
            Nodo_Siguiente = NombreHilos.get(indice + 1);
        }

        return Nodo_Siguiente;
    }

    public String getHilosVivos(String nombreHilo) {
        return "" + HilosVivos;
    }

    /*
        #Funcion HiloEstaEn_ListaEjecucion: Creada con el fin de verificar si el hilo que corra este codigo ya
          se encuentre dentro de la lista de ejecucion.
        #Entrada: nombreHilo, el cual es el ID del hilo en cuesiton
        #Salida: Regresa falso si el elemento no esta en la lista de ejecucion, caso contrario true
     */
    public boolean HiloEstaEn_ListaEjecucion(String nombreHilo) {
        boolean existe;
        Object hilo = nombreHilo;
        if ((NombreHilos.indexOf(hilo)) == -1) {
            existe = false; //El elemento no existe dentro del arrayList
        } else {
            existe = true; //El elemento Si existe dentro del arrayList
        }
        return existe;
    }

    /*
        #Funcion AlmacenaNombres: Creada con el fin de ir llenando la lista que sera el orden en el cual
          se ejecutara cada hilo para la siguiente etapa, es decir, inicializamos la lista y la llenamos.
        #Entrada: nombreHilo, el cual es el ID del hilo en cuesiton
        #Salida: void - no regresa nada
     */
    public void AlmacenaNombres(String nombreHilo) {
        try {
            //Bloqueamos seccion critica
            semaforo.acquire();

            //1) Si la Lista esta vacia la inicializamos
            if (NombreHilos.isEmpty()) {
                if (!HiloEstaEn_ListaEjecucion(nombreHilo)) {//Agregamos el hilo a la lista de ejecucion
                    NombreHilos.add(nombreHilo);
                    PosicionRecorrida++;
                    //System.out.println("Primer elemento: " + nombreHilo + " Posicion recorrida: " + PosicionRecorrida + " \t HilosMaximos: " + MaximoHilos);
                }
                HiloSeleccionado = "";
                HilosUsados = 0;
            }

            //2) Si hay elementos por agregar en la ejecucion intermedia se agregan
            if (PosicionRecorrida > 0 && PosicionRecorrida < MaximoHilos - 1) {
                if (!HiloEstaEn_ListaEjecucion(nombreHilo)) {//Agregamos el hilo a la lista de ejecucion
                    NombreHilos.add(nombreHilo);
                    PosicionRecorrida++;
                    //System.out.println("\nElemento anterior: " + NombreHilos.get(PosicionRecorrida - 2));
                    //System.out.println("Elemento Intermedio: " + nombreHilo + " Posicion recorrida: " + PosicionRecorrida + " \t HilosMaximos: " + MaximoHilos);
                }
            }

            //3) Si ya solo es el ultimo elemento volvemos la lista Circular
            if (PosicionRecorrida == MaximoHilos - 1) {
                if (!HiloEstaEn_ListaEjecucion(nombreHilo)) {//Agregamos el hilo a la lista de ejecucion
                    NombreHilos.add(nombreHilo);
                    PosicionRecorrida++;
                    HilosVivos = PosicionRecorrida;
                    System.out.println("Hilos Vivos:" + HilosVivos);
                    //System.out.println("\nElemento anterior: " + NombreHilos.get(PosicionRecorrida - 2));
                    //System.out.println("Ultimo elemento: " + nombreHilo + " Posicion recorrida: " + PosicionRecorrida + " \t HilosMaximos: " + MaximoHilos);
                    ListaLlena = true;
                    System.out.println("\n\t --- ListaEjecucion Llenada: " + ListaLlena + " ---");
                    //Imprimimos el orden de ejecucion de los hilos
                    System.out.println("Orden de ejecucion: \n");
                    for (String elemento : NombreHilos) {
                        System.out.print(elemento + " ");
                    }
                    System.out.println("\n");
                }
            }

            //Desbloqueamos seccion critica
            semaforo.release();
        } catch (InterruptedException error) {
            System.out.println("Error al llenar la lista de ejecucion: " + error.getMessage());
        }
    }

    /*
        #Funcion BuscaArchivo: Creada con el fin de ir buscando el archivo solicitado pero de manera ordenada
          y con base en el orden que nos genera la lista circular.
        #Entrada: 
            - nombreHilo: ID del hilo en cuesiton
            - nombreArchivo: nombre del archivo que se busca en cuestion
        #Salida: void - no regresa nada
     */
    public void BuscaArchivo(String nombreHilo, String nombreArchivo, String anterior, String Siguiente, Ventana ventanita) {
        try {
            //Bloqueamos seccion critica
            semaforo.acquire();

            //Creamos ruta para buscar el archivo
            String rutaArchivoTemporal = "";
            rutaArchivoTemporal += "./01/";
            rutaArchivoTemporal += nombreHilo;
            rutaArchivoTemporal += "/";
            rutaArchivoTemporal += nombreArchivo;

            if (AlguienYaEmpezoABuscar == true) {
                sleep(2000);
                ventanita.jta_Notificaciones.append("===== Hilo " + anterior + " no encontro el archivo =====\n");
            }
            sleep(2000);
            ventanita.jta_Notificaciones.append(nombreHilo + " > " + "Buscando el archivo en:  " + rutaArchivoTemporal + "\n");

            //Creamos objeto del tipo archivo y validamos si existe
            File archivito = new File(rutaArchivoTemporal);
            if (archivito.exists()) {//Si el archivo existe entonces paramos el bucle

                if (AlguienYaEmpezoABuscar == false) {
                    AlguienYaEmpezoABuscar = true;
                    HiloIniciaBusqueda = nombreHilo; //En este caso el primer hilo en ejecutarse ya encontro el archivo
                }

                HiloAvisador = nombreHilo;
                HiloReferencia = nombreHilo;
                sleep(2000);
                ventanita.jta_Notificaciones.append(nombreHilo + " > Ya lo encontre, ¡NADIE BUSQUE! \n");                
                if(HiloIniciaBusqueda.equals(HiloAvisador)){//Si se trata del primer hilo en ejecutarse en encontrar el archivo
                    sleep(10);
                }else{
                    sleep(2000);
                    ventanita.jta_Notificaciones.append(nombreHilo + " > Le paso el path al " + anterior + " \n");
                }                
                RespuestaFinal = "¡EXITO! > Path Correcto:    " + rutaArchivoTemporal + "     Encontrado por: " + nombreHilo;
                System.out.println("InicioBuscando: " + HiloIniciaBusqueda);
                System.out.println("Encontro: " + HiloAvisador);
                ArchivoEncontrado = true;
                HiloSeleccionado = "";
                TodosEstanRecorridos = true;

            } else {//Si el archivo no se encuentra pasa lo siguiente

                if (HilosUsados == MaximoHilos - 1) {//Si ya recorrimos la cantidad de hilos diponibles terminamos el bucle
                    HilosUsados = 0; //Reiniciamos el contador por si se vuelve a buscar
                    HiloSeleccionado = "";
                    HiloAvisador = nombreHilo;
                    sleep(2000);
                    ventanita.jta_Notificaciones.append(nombreHilo + " > No encontre el archivo, avisando al siguiente: \n\n");
                    RespuestaFinal = "> NO SE ENCONTRO EL ARCHIVO \n";
                    System.out.println("Ultimo quien tiene que empezar a Avisar: " + HiloAvisador);
                    System.out.println("Primero al que hay que llegar: " + HiloIniciaBusqueda);
                    ArchivoEncontrado = false;
                    TodosEstanRecorridos = true;
                    //Avisar a los anteriores de que no se encontro el archivo
                } else {//Si aun no llegamos al final seguimos sumando
                    sleep(2000);
                    ventanita.jta_Notificaciones.append(nombreHilo + " > No lo encontre, Hilo que sigue: " + Siguiente + "\n");
                    HiloSeleccionado = Siguiente;

                    if (AlguienYaEmpezoABuscar == false) {
                        AlguienYaEmpezoABuscar = true;
                        HiloIniciaBusqueda = nombreHilo;
                    }

                    HilosUsados++;
                }

            }

            //Liberamos seccion critica
            semaforo.release();
        } catch (InterruptedException error) {
            System.out.println("Error al llenar la lista de ejecucion: " + error.getMessage());
        }
    }

    /*
        #Funcion Avisa_Encontrado: Creada con el fin de ir avisando a los nodos que ya realizaron una busqueda
          previa del archivo en cuestion, ir de manera regresiva hasta que el primer nodo que inicio la busqueda
          se entere que ya se encontro el archivo.
        #Entrada: 
            - nombreHilo: ID del hilo en cuesiton            
        #Salida: void - no regresa nada
     */
    public void Avisa_Encontrado(String nombreHilo, String anterior, String Siguiente, Ventana ventanita) {
        try {
            //Bloqueamos seccion critica
            semaforo.acquire();

            if (HiloReferencia.equals(nombreHilo)) {//Si se trata del que tiene que iniciar a avisar
                if (HiloIniciaBusqueda.equals(nombreHilo)) {//Si el primer hilo encontro el archivo entonces
                    sleep(2000);
                    ventanita.jta_Notificaciones.append(RespuestaFinal);
                    TodosEstanAvisados = true;
                } else {//Si no fue el primer hilo
                    sleep(2000);
                    ventanita.jta_Notificaciones.append(RespuestaFinal);
                    HiloAvisador = anterior;
                }
            } else if (HiloIniciaBusqueda.equals(nombreHilo)) {//Si se trata del ultimo a quien se le tiene que avisar
                sleep(2000);
                ventanita.jta_Notificaciones.append("\n===== El " + Siguiente + " me notificó lo siguiente: =====\n" + RespuestaFinal);
                sleep(2000);
                ventanita.jta_Notificaciones.append("\n\n>>>>> Último hilo en ser avisado <<<<< \n");
                TodosEstanAvisados = true;
            } else {
                sleep(2000);
                ventanita.jta_Notificaciones.append("\n===== El " + Siguiente + " me notificó lo siguiente: =====\n" + RespuestaFinal);
                HiloAvisador = anterior;
            }

            //Liberamos seccion critica
            semaforo.release();
        } catch (InterruptedException error) {
            System.out.println("Error al avisar exito obtenido a los nodos: " + error.getMessage());
        }
    }

    /*
        #Funcion Avisa_NO_Encontrado: Creada con el fin de ir avisando a todos los nodos que no se encontro el archivo,
          partiendo desde el ultimo nodo que no logro encontrar el archivo.
        #Entrada:
            - nombreHilo: ID del hilo en cuesiton
        #Salida: void - no regresa nada
     */
    public void Avisa_NO_Encontrado(String nombreHilo, String anterior, String Siguiente, Ventana ventanita) {
        try {
            //Bloqueamos seccion critica
            semaforo.acquire();

            if (HilosUsados == MaximoHilos - 1) {//Si ya recorrimos la cantidad de hilos diponibles terminamos el bucle

                RespuestaFinal = "> NO SE ENCONTRO EL ARCHIVO \n";
                sleep(2000);
                ventanita.jta_Notificaciones.append("\n===== El " + Siguiente + " me notificó lo siguiente: =====\n" + RespuestaFinal);
                sleep(2000);
                ventanita.jta_Notificaciones.append("\n\n>>>>> Último hilo en ser avisado <<<<< \n");
                TodosEstanAvisados = true;
                HiloAvisador = "";

            } else {//Si aun no llegamos al final seguimos sumando

                if (AlguienYaEmpezoAvisar == false) {
                    sleep(2000);
                    ventanita.jta_Notificaciones.append(RespuestaFinal);
                    AlguienYaEmpezoAvisar = true;
                } else {
                    sleep(2000);
                    ventanita.jta_Notificaciones.append("\n===== El " + Siguiente + " me notificó lo siguiente: =====\n" + RespuestaFinal);
                }

                HiloAvisador = anterior;

                HilosUsados++;
            }

            //Liberamos seccion critica
            semaforo.release();
        } catch (InterruptedException error) {
            System.out.println("Error al avisar exito obtenido a los nodos: " + error.getMessage());
        }
    }

}
