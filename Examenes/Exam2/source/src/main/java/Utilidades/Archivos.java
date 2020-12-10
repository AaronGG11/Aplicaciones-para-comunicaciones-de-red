package Utilidades;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Archivos {
    private static final int BUFFER_SIZE = 20000000;

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
        images_path.append("customer_resources");
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

    public static void descomprimirZIP(String nombre_zip){
        // Path de carpeta de imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("customer_resources");
        images_path.append(File.separator);

        File directorio = new File(images_path.toString() + nombre_zip);

    }

    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);

        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public static void eliminarArchivo(String path){
        File fichero = new File(path);

        if (fichero.delete())
            System.out.println("El archivo zip ha sido borrado satisfactoriamente");
        else
            System.out.println("El archivo zip no pudó ser borrado");
    }


}