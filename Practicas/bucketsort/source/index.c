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


void imprimirResumen(int numero_hilos, pthread_t hilos_id[], int limite_inferior, int limite_superior, int total_numeros, int rangos[], Lista numeros[]){
    printf("\nIPN ESCOM 3CM8 - Aplicaciones para comunicaciones en red - Practica Ordanamiento por canastas\n\n");
    printf("Resumen de ejecución:\n");
    printf("\t-Número de hilos/servidores: %d\n", numero_hilos);
    printf("\t-Rango de numeros aleatorios: %d - %d\n", limite_inferior, limite_superior);
    printf("\t-Total de números aleatorios generados: %d\n", total_numeros);
    imprimirRangos(rangos, hilos_id, numeros, numero_hilos);
    printf("\t-Archivo resultado.txt generado correctamente\n\n");
}


int main(int argc, char const *argv[])
{
    int numero_hilos = atoi(argv[1]);
    int rangos[numero_hilos], error;
    pthread_t hilos_id[numero_hilos];
    Lista numeros[numero_hilos];

    int limite_inferior = 0;
    int limite_superior = 999;
    int total_numeros = 3500;

    generarRangos(rangos, limite_inferior, limite_superior, numero_hilos);
    FuncionAleatoria(numero_hilos, numeros, rangos, limite_inferior, limite_superior, total_numeros);
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


    imprimirResumen(numero_hilos, hilos_id, limite_inferior, limite_superior, total_numeros, rangos, numeros);
    


    return 0;
}