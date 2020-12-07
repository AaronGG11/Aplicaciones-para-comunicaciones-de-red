import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Suma extends Remote {
    int suma(int a,int b) throws RemoteException;
}
