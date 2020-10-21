
#ifndef __ERRORES__
#define __ERRORES__

#define error_fatal(codigo, texto) do{\
    fprintf(stderr, "%s:%d: Error: %s - %s\n",\
    __FILE__, __LINE__, texto, strerror(codigo));\
    abort();\
    } while (0)
#endif