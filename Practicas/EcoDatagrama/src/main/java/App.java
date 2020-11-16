import java.util.Hashtable;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Map<String, StringBuilder> ejemplo = new Hashtable<>();

        ejemplo.put("aaron", new StringBuilder());
        ejemplo.get("aaron").append("fddssdd");
        ejemplo.get("aaron").append(" vfdc bvf vcd");

        System.out.println(ejemplo);

    }
}
