#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <pthread.h>

int fd[2];
void *lector(){
    while(1){
       char    ch;
       int     resultado;
       resultado = read (fd[0],&ch,1);
       if (resultado != 1) {
            perror("Error en read");
            exit(3);
    }    printf ("Lector: %c\n", ch);  }
}

void *escritor_ABC(){
     int     resultado;
     char    ch='A';
     while(1){
           resultado = write (fd[1], &ch,1);
           if (resultado != 1){
               perror ("Error en write");
               exit (2);
           }
           printf ("Escritor_ABC: %c\n", ch);
           if(ch == 'Z')
              ch = 'A'-1;
           ch++;
      }
}

void *escritor_abc(){
  int     resultado;
  char    ch='a';
  while(1){
      resultado = write (fd[1], &ch,1);
      if (resultado != 1){
            perror ("Error en write");
            exit (2);
      }
      printf ("Escritor_abc: %c\n", ch);
      if(ch == 'z')
            ch = 'a'-1;
     ch++;
  }
}

int main(){
    pthread_t tid1,tid2,tid3;
    int resultado;
    resultado = pipe (fd);
    if (resultado < 0){
       perror("Error en pipe ");
       exit(1);
    }
    pthread_create(&tid1,NULL,lector,NULL);
    pthread_create(&tid2,NULL,escritor_ABC,NULL);
    pthread_create(&tid3,NULL,escritor_abc,NULL);
    pthread_join(tid1,NULL); 
    pthread_join(tid2,NULL);
    pthread_join(tid3,NULL);
}
