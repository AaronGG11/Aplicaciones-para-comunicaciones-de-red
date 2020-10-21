#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include "./library/Lista/Elem.h"
#include "./library/Lista/Lista.h"
#include "./library/errores/Errores.h"
#include "./library/BucketSort.h"
#include "./library/ArchivoTXT.h"


int main(int argc, char const *argv[])
{
    int numero_hilos = atoi(argv[1]);
    int rangos[numero_hilos], error;
    pthread_t hilos_id[numero_hilos];
    Lista numeros[numero_hilos];

    generarRangos(rangos, 0, 999, numero_hilos);
    FuncionAleatoria(numero_hilos, numeros, rangos, 0, 999, 3500);
    limpiarAchivo();

    // Creando hilos
    for(int i=0; i<numero_hilos; i++){

        error = pthread_create(&hilos_id[i], NULL, ordenar, &numeros[i]);
        imprimirNumeros(hilos_id[i], numeros[i], 1);

        if(error){
            printf("No se pudo crear el hilo %d\n", i+1);
        }
    }

    // Sincronizacion de hilos
    for(int i=0; i<numero_hilos; i++){
        pthread_join(hilos_id[i], (void **)&numeros[i]);
        imprimirNumeros(hilos_id[i], numeros[i], 2);
    }

    Lista lista_resultado = concatenarArregloListas(numeros, numero_hilos);
    imprimirNumeros(0, lista_resultado, 3);
    


    return 0;
}