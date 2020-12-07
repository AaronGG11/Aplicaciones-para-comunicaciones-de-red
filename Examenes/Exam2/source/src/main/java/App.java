import model.Utilidades;
import view.Board;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args)
    {


        StringBuilder images_path = new StringBuilder();
        images_path.append("..");
        images_path.append(File.separator);
        images_path.append("images");
        images_path.append(File.separator);

        try {
            BufferedImage image = ImageIO.read(new File(images_path.toString()+"fondo.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( image, "jpg", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
