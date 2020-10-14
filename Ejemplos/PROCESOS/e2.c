#include <stdio.h>     //biblioteca estandar
#include <unistd.h>    //biblioteca para uso de fork
#include <sys/types.h> //biblioteca para uso de fork
#include <stdlib.h>    //biblioteca para uso de exit
#include <sys/wait.h>  //biblioteca para uso de wait

#define NUM_PROCESOS 5

int I = 0;

void codigo_del_proceso(int id)
{
    int i;
    for (i = 0; i < 50; i++)
        printf("Proceso %d: i = %d, I = %d\n", id, i, I++);
    // El valor de id se almacena en los bits 8 al 15
    // antes de devolver al proceso padre
    exit(id);
}

int main()
{
    int id[NUM_PROCESOS] = {1, 2, 3, 4, 5};
    int p, pid, salida;

    for (p = 0; p < NUM_PROCESOS; p++)
    {
        pid = fork();
        if (pid == -1)
        {
            perror("Error al crear un proceso: ");
            exit(-1);
        }
        else if (pid == 0) //Codigo del proceso hijo
            codigo_del_proceso(id[p]);
    }

    //Esta parte solo la ejecuta el proceso padre
    for (p = 0; p < NUM_PROCESOS; p++)
    {
        pid = wait(&salida);
        printf("Proceso %d con id = %x terminado\n",
               pid, salida >> 8);
        //El id del proceso devuelto con la llamada a
        // exit se almacena en los bits 8 al 15
    }

    return 0;
}
