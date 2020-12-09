import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        String directory = "...";
        String hostDomain = "localhost";
        int port = 9000;

        File[] files = new File(directory).listFiles();

        Socket socket = new Socket(InetAddress.getByName(hostDomain), port);

        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt(files.length);

        for(File file : files)
        {
            long length = file.length();
            dos.writeLong(length);

            String name = file.getName();
            dos.writeUTF(name);

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            int theByte = 0;
            while((theByte = bis.read()) != -1) bos.write(theByte);

            bis.close();
        }

        dos.close();
    }
}

