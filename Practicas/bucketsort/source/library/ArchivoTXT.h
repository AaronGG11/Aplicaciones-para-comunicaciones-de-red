void imprimirNumeros(int hilo_id, Lista numeros, int tipo){
    Lista auxiliar = numeros;

    FILE * archivo;
    archivo = fopen("resultado.txt", "a+");

    if(tipo == 1)
        fprintf(archivo, "Lista [NO ORDENADA][%d] ... # Elementos: %d\n", hilo_id, NumElem(numeros));
    else if(tipo == 2)
        fprintf(archivo, "Lista [SI ORDENADA][%d] ... # Elementos: %d\n", hilo_id, NumElem(numeros));
    else 
        fprintf(archivo, "Lista [RESULTADO][%d] ... # Elementos: %d\n", hilo_id, NumElem(numeros));

    while(!esVacia(auxiliar)){
        fprintf(archivo, "%d ", Cabeza(auxiliar));
        auxiliar = Resto(auxiliar);
    }

    fprintf(archivo, "\n\n");

    fclose(archivo);
    free(auxiliar);
}


void limpiarAchivo(){
    FILE * archivo;
    archivo = fopen("resultado.txt", "w+");

    fclose(archivo);
}


