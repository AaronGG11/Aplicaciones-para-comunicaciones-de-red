/*
Funcion que establece los rangos dado limite superior e inferior, asi como el numero
de segmentaciones, regresando un arreglo con los topes superiores.
*/
void generarRangos(int rangos[], int limite_inferior, int limite_superior, int particiones){
    int inicio = 0;
    int fin = 999;

    int salto = (fin-inicio+1)/particiones;

    // genenrando rangos
    for(int i=0; i<particiones; i++){
        if(i==0){
            rangos[i] = salto-1;
        }
        else{
            rangos[i] = rangos[i-1] + salto;
        }
    }
}



void imprimirRangos(int rangos[], pthread_t hilos_id[], Lista numeros[], int numero_hilos){
   for(int i=0; i<numero_hilos; i++){
        if(i==0)
            printf("\t-Lista: %d\t-Rango: [%d - %d]\tNúmero de elementos: %d\n", (int)hilos_id[i], 0, rangos[i], NumElem(numeros[i]));
        else
            printf("\t-Lista: %d\t-Rango: [%d - %d]\tNúmero de elementos: %d\n", (int)hilos_id[i], rangos[i-1]+1, rangos[i], NumElem(numeros[i]));
    }
}




/* Funcion que genera 3500 numeros de manera aleatoria y los almacena en 
    listas de acuerdo al rango creado a partir del numero de hilos a trabajar. 
*/
void FuncionAleatoria(int numero_hilos, Lista numeros[], int rangos[], int inicio, int fin, int total){
    // Inicializando listas vacias
    for(int i=0; i<numero_hilos; i++){
        numeros[i] = Vacia();
    }

    // Llenando cada lista dada por el rango
    srand(time(NULL));
    for(int i=0; i<total; i++){
        int numero_aleatorio = inicio + rand()%((fin+1)-inicio);

        for(int j=0; j<numero_hilos; j++){
            if(j==0){
                if(numero_aleatorio<=rangos[0]){
                    numeros[0] = Cons(numero_aleatorio, numeros[0]);
                }
            }
            else{
                if((numero_aleatorio>rangos[j-1])&&(numero_aleatorio<=rangos[j])){
                    numeros[j] = Cons(numero_aleatorio, numeros[j]);
                }
            }
        }
    }
}


void *ordenar(void *numeros){
    int estado_anterior, error;
    error = pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &estado_anterior);
    
    if(error)
        error_fatal(error, "pthread_setcancelstate");
    
    OrdLista(*(Lista*)numeros);

}


Lista concatenarArregloListas(Lista numeros[], int numero_hilos){
    int contador = 0;
    Lista resultado = Vacia();
    for(int i=0; i<numero_hilos; i++){
        if(i==0){
            resultado = PegaListas(numeros[i], Vacia());
        }
        else{
            resultado = PegaListas(resultado, numeros[i]);
        }
    }

    return resultado;
}
