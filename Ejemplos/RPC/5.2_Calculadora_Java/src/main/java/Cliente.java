import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {
    private Cliente() {}
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            //también puedes usar getRegistry(String host, int port)
            Calculadora stub = (Calculadora) registry.lookup("Calculadora");

            int x=5,y=4;
            int response_suma = stub.suma(x,y);
            int response_resta = stub.resta(x,y);
            int response_multiplicacion = stub.multiplicacion(x,y);
            float response_division = stub.division(x,y);

            System.out.println("respuesta sumar "+x+" y "+y+" : " + response_suma);
            System.out.println("respuesta restar "+x+" y "+y+" : " + response_resta);
            System.out.println("respuesta multiplicar "+x+" y "+y+" : " + response_multiplicacion);
            System.out.println("respuesta dividir "+x+" y "+y+" : " + response_division);
        } catch (Exception e) {
            System.err.println("Excepción del cliente: " +e.toString());
            e.printStackTrace();
        }
    }
}
