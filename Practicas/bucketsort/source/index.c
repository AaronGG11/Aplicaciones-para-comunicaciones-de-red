#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <pthread.h>
#include "./library/Lista/Elem.h"
#include "./library/Lista/Lista.h"
#include "./library/BucketSort.h"


int main(int argc, char const *argv[])
{
    int numero_hilos = atoi(argv[1]);
    int hilos_id[numero_hilos], error;
    int rangos[numero_hilos];
    pthread_t hilos[numero_hilos];
    Lista numeros[numero_hilos];

    generarRangos(rangos, 0, 999, numero_hilos);
    FuncionAleatoria(numero_hilos, numeros, rangos, 0, 999, 3500);

    //ImpLista(concatenarArregloListas(numeros, numero_hilos));

    // Creando hilos
    for(int i=0; i<numero_hilos; i++){
        hilos_id[i] = i+1;

        error = pthread_create(&hilos[i], NULL, ordenar, &numeros[i]);

        if(error){
            printf("No se pudo crear el hilo %d\n", i+1);
        }
    }

    // Sincronizacion de hilos
    for(int i=0; i<numero_hilos; i++){
        pthread_join(hilos[i], (void **)&numeros[i]);
    }


    return 0;
}