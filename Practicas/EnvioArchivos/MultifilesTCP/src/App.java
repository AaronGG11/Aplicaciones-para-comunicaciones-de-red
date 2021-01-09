import java.io.File;

public class App {
    public static void main(String[] args) {
        StringBuilder images_path = new StringBuilder();
        images_path.append(".");
        images_path.append(File.separator);
        images_path.append("middle");
        images_path.append(File.separator);

        File carpeta = new File(images_path.toString());
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        }
        else {
            for (int i=0; i< listado.length; i++) {
                System.out.println(listado[i]);
            }
        }
    }
}
