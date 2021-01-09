import java.io.IOException;
import java.net.Socket;


/**
 * @author Aaron Garcia
 */

public class Final {
    public static void main(String[] args) {
        // This is just a client
        StringBuilder LOCAL_PATH = new StringBuilder();


        // Server's credentials to connect
        String HOST = "localhost";
        Integer PORT = 7001;

        try {
            Socket cliente = new Socket(HOST, PORT);


            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
