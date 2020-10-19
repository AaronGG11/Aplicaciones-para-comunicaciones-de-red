#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include "./library/Elem.h"
#include "./library/Lista.h"

void FuncionAleatoria(int numero_hilos, Lista numeros_rangos[]);


int main(int argc, char const *argv[])
{
    int numero_hilos = atoi(argv[1]);

    Lista numeros[numero_hilos];
    FuncionAleatoria(numero_hilos, numeros);

    return 0;
}


/* Funcion que genera 3500 numeros de manera aleatoria y los almacena en 
    listas de acuerdo al rango creado a partir del numero de hilos a trabajar. 
*/
void FuncionAleatoria(int numero_hilos, Lista numeros[]){
    int total = 3500;
    int inicio = 0;
    int fin = 999;

    int salto = (fin-inicio+1)/numero_hilos;
    int rangos[numero_hilos];

    // genenrando rangos
    for(int i=0; i<numero_hilos; i++){
        if(i==0){
            rangos[i] = salto-1;
        }
        else{
            rangos[i] = rangos[i-1] + salto;
        }
    }

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
