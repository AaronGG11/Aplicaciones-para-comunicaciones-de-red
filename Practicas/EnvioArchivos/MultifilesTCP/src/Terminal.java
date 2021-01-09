import java.io.File;
import java.io.IOException;
import java.net.Socket;


/**
 * @author Aaron Garcia
 */

public class Terminal {
    public static void main(String[] args) {
        // This is just a client
        StringBuilder LOCAL_PATH = new StringBuilder();
        LOCAL_PATH.append(".");
        LOCAL_PATH.append(File.separator);
        LOCAL_PATH.append("terminal_files");
        LOCAL_PATH.append(File.separator);


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
