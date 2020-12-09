import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculadora extends Remote {
    int suma(int a,int b) throws RemoteException;
    int resta(int a,int b) throws RemoteException;
    int multiplicacion(int a,int b) throws RemoteException;
    float division(int a,int b) throws RemoteException;
}
