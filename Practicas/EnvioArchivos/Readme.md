# Práctica: Envio de multiples archivos (Drive)
- Option #1: Carpeta MultifilesTCP (60%)
- Option #2: Carpeta Source (100%)

## Descripción
Implementar una aplicación para el envío de múltiples archivos a través de la red haciendo uso de sockets de flujo.

![Diagrama de funcionamiento](https://github.com/AaronGG11/Aplicaciones-para-comunicaciones-de-red/blob/master/Practicas/EnvioArchivos/images/diagrama.png?raw=true)

## Requerimientos
- Implementar un servicio de transferencia de archivos para que el cliente de la aplicación pueda enviar uno o más archivos de cualquier tamaño y tipo hacia el servidor.
-  Desde la aplicación cliente, la posibilidad demodificar parámetros de la comunicación  para modificar el desempeño de la aplicación, mediante el uso de opciones de socket:
	- Habilitar/deshabilitar el algoritmo de Nagle.
	- Modificar el tamaño de los buffers de escritura/lectura.
- Initial
	- Funciona como servidor.
	- Carga archivos mediante Java chooser.
	- Envia archivos a los clientes (middle) que lo solicitan.
- Middle
	- Funciona como cliente y servidor.
	- Inmediatamente al iniciar, la parte cliente solicita archivos al servidor (initital).
	- Guarda archivos recibidos en su carpeta local.
	- Envia archivos a los clientes (terminal) que lo solicitan.
- Terminal
	- Funciona como cliente.
	- Inmediatamente al iniciar, solicita archivos al servidor (middle).
	- Guarda archivos recibidos en su carpeta local.

## Código base
#### Servidor
```java
import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args){
        try{
            // Creamos el socket
            ServerSocket s = new ServerSocket(7000);
            // Iniciamos el ciclo infinito del servidor
            for(;;){
               // Esperamos una conexión 
               Socket cl = s.accept();
                System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];
                String nombre = dis.readUTF();
                System.out.println("Recibimos el archivo:"+nombre);
                long tam = dis.readLong();
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                long recibidos=0;
                int n, porcentaje;
                while(recibidos < tam){
                    n = dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    recibidos = recibidos + n;
                    porcentaje = (int)(recibidos*100/tam);
                    System.out.print("Recibido: "+porcentaje+"%\r");
                }//While
                System.out.print("\n\nArchivo recibido.\n");
                dos.close();
                dis.close();
                cl.close();
            }
        }catch(Exception e){
                e.printStackTrace();
        }//catch
    }
}
```

#### Cliente
```java
import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Escriba la dirección del servidor:");
            String host = br.readLine();
            System.out.printf("\n\nEscriba el puerto:");
            int pto = Integer.parseInt(br.readLine());
            Socket cl = new Socket(host, pto);
            JFileChooser jf = new JFileChooser();
            int r = jf.showOpenDialog(null);
            if (r==JFileChooser.APPROVE_OPTION){
                File f = jf.getSelectedFile();  //Manejador
                String archivo = f.getAbsolutePath(); //Dirección
                String nombre = f.getName(); //Nombre
                long tam = f.length();  //Tamaño
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(archivo));          
                dos.writeUTF(nombre);
                dos.flush();               
                dos.writeLong(tam);
                dos.flush();
                byte[] b = new byte[1024];
                long enviados = 0;
                int porcentaje, n;
                while (enviados < tam){
                     n = dis.read(b);
                     dos.write(b,0,n);
                     dos.flush();
                     enviados = enviados+n;
                     porcentaje = (int)(enviados*100/tam);
                     System.out.print("Enviado: "+porcentaje+"%\r");
                 }//While
                System.out.print("\n\nArchivo enviado");
                dos.close();
                dis.close();
                cl.close();
            }//if
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
```

### Algoritmo de Nagle
El Algoritmo de Nagle se trata de un procedimiento que supone una mejora y aumento de eficiencia de las redes de comunicación basadas en Transmission Control Protocol (TCP). El algoritmo de Nagle es un método heurístico para evitar enviar paquetes IP particularmente pequeños, también denominados pequegramas. El algoritmo de Nagle intenta evitar la congestión que estos paquetes pueden ocasionar en la red reteniendo por poco tiempo la transmisión de datos TCP en algunas circunstancias.

```
if hay nuevos datos en enviar
 if el tamaño de ventana y su disponibilidad de datos es >= MSS
  envía el tamaño de MSS ahora
 else
  if hay datos sin confirmar en espera
   encola los datos en el buffer hasta recibir un reconocimiento
  else
   envía los datos ahora
  end if
 end if
end if
```


## Pruebas
Realiza pruebas intentando enviar distintos tipos de archivo (imágenes, texto, ejecutables), así mismo intenta enviar archivos de distintos tamaños (menos de 100KB, más de 100KB y menos de 10MB, más de 10MB y menos de 200MB, más de 200MB y hasta 2GB).
