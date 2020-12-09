package Utilidades;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memorama {
    public static List<String> obtenerNombresImagenes(){
        List<String> resultado = new ArrayList<>();

        for(int i=1; i<=20; i++){
            resultado.add("imagen" + i + ".jpg");
            if(i==5){   resultado.addAll(resultado.subList(0,5)); }
            if(i==10){  resultado.addAll(resultado.subList(10,15)); }
            if(i==15){  resultado.addAll(resultado.subList(20,25)); }
            if(i==20){  resultado.addAll(resultado.subList(30,35)); }
        }

        return resultado;
    }


    public static List<String> obtenerOrdenImagenes(Boolean orden){
        List<String> resultado = new ArrayList<>();

        resultado = obtenerNombresImagenes();

        if(!orden){ Collections.shuffle(resultado); }

        return resultado;
    }

    public static void crearCarpeta(String nombre_carpeta){
        // Path de carpeta de imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("customers");
        images_path.append(File.separator);

        File directorio = new File(images_path + nombre_carpeta);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio " + nombre_carpeta + " creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }

    public static void eliminarCarpeta(String nombre_carpeta){
        // Path de carpeta de imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("customers");
        images_path.append(File.separator);

        File directorio = new File(images_path + nombre_carpeta);
        if (directorio.exists()) {
            // Eliminar contendio
            for(File archvio:  directorio.listFiles()){
                archvio.delete();
            }

            if (directorio.delete()) {
                System.out.println("Directorio " + nombre_carpeta + " eliminado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }


}