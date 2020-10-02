#include <stdio.h>
#include <unistd.h>

int main(int argc, char const *argv[])
{
    int i = 0;

    switch (fork())
    {
    case -1:
        perror("Algo fallo");
        break;
    case 0:
        while (i < 10)
        {
            sleep(1);
            printf("Soy el hijo: %d\n", i++);
        }
        break;
    default:
        while (i < 10)
        {
            sleep(1);
            printf("Soy el padre: %d\n", i++);
        }
        break;
    }

    return 0;
}
