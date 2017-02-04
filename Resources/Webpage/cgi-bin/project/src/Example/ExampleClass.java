package Example;
import java.util.Random;

public class ExampleClass {
    public static String testMethod() {
        Random r = new Random();
        return Integer.toString(r.nextInt());
    }
}
