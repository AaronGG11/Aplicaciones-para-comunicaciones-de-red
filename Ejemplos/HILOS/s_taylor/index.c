#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <string.h>
#include "errores.h"


//Presenta el resultado final del cálculo
void fin_del_calculo(void *arg){
        double resultado = *(double *)arg;
        printf("Resultado final: %g\n",resultado);
}

//Cálculo de la serie de Taylor
void *calculo(void *arg){
    int error, estado_ant, tipo_ant;
    double x = *(double *)arg, resultado = 1, sumando = 1;
    long i, j;

    //Parte obligatoria del cálculo
    //Deshabilitamos la posibilidad de cancelar el hilo
    error=pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &estado_ant);
    if (error) error_fatal(error, "pthread_setcancelstate");
    for(i = 1; i<10; i++){
        sumando *= x/i;
        resultado += sumando;
    }

    printf("Primera aproximación de exp(%g) = %g\n", x, resultado);
    pthread_cleanup_push(fin_del_calculo, &resultado);

	/*Una vez ejecutada la parte obligatoria habilitamos 
	  la posibilidad de cancelar el hilo */
    error = pthread_setcanceltype(PTHREAD_CANCEL_DEFERRED,&tipo_ant);
    if(error) error_fatal(error, "pthread_setcanceltype");
    error = pthread_setcancelstate(PTHREAD_CANCEL_ENABLE,&estado_ant);
    if(error) error_fatal(error, "pthread_setcancelstate");
    //En esta parte de refinamiento del cálculo se aceptan peticiones de cancelación
    printf("Refinamiento del cálculo\n");
    for(;;){
        pthread_testcancel(); //Punto de cancelación
        //Si no hay petición de cancelación se ejecuta un nuevo refinamiento del cálculo
        for(j=0; j<10; j++,i++){
            sumando *= x/i;
            resultado += sumando;
        }
    }
    pthread_cleanup_pop(1);
}


int main(int argc, char const *argv[])
{
    
    pthread_t hilo;
    int error, plazo;
    double x;

    //Análisis de los argumentos
    if(argc != 3){
        printf("Forma de uso: %s x plazo\n", argv[0]);
        exit(-1);
    } 
    else {
        x = atof(argv[1]);
        plazo = atoi(argv[2]);
    }

    //Creación del hilo que realiza el cálculo
    error = pthread_create(&hilo, NULL, calculo, &x);
    if(error) error_fatal(error, "pthread_create");

    //una vez concluido el plazo se cancela el hilo que calcula
    sleep(plazo);
    error = pthread_cancel(hilo);
    if(error) error_fatal(error, "pthread_cancel");

    //Esperamos hasta que la cancelación se haga efectiva
    error = pthread_join(hilo, NULL);
    if(error) error_fatal(error, "pthread_join");

    return 0;
}
