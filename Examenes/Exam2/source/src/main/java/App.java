import Utilidades.Archivos;

import java.io.File;

public class App {
    public static void main(String[] args)
    {
        // Path de carpeta con imagenes
        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);



        File sourceFile = new File(images_path.toString() + "prueba" );
        File destFile = new File(images_path.toString() + "algo" );

        if (sourceFile.renameTo(destFile)) {
            System.out.println("Directory renamed successfully");
        } else {
            System.out.println("Failed to rename directory");
        }
    }
}
