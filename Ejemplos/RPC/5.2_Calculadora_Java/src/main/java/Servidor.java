import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor implements Calculadora {
    public Servidor() {}

    public int suma(int a, int b) {
        System.out.println("Realizando suma");
        return a+b;
    }

    @Override
    public int resta(int a, int b) throws RemoteException {
        System.out.println("Realizando resta");
        return a-b;
    }

    @Override
    public int multiplicacion(int a, int b) throws RemoteException {
        System.out.println("Realizando multiplicacion");
        return a*b;
    }

    @Override
    public float division(int a, int b) throws RemoteException {
        System.out.println("Realizando division");
        if(b==0) {return 0;}
        else {return (float) a/b;}
    }

    public static void main(String args[]) {
        try {
            //puerto default del rmiregistry
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registro listo.");
        } catch (Exception e) {
            System.out.println("Excepcion RMI del registry:");
            e.printStackTrace();
        }//catch
        try {
            //System.setProperty("java.rmi.server.codebase","file:/c:/Temp/Suma/"); windows
            // en linux lo hace en auto
            Servidor obj = new Servidor();
            Calculadora stub = (Calculadora) UnicastRemoteObject.exportObject(obj, 0);
            // Ligamos el objeto remoto en el registro
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Calculadora", stub);

            System.err.println("Servidor listo...");
        } catch (Exception e) {
            System.err.println("Excepción del servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
