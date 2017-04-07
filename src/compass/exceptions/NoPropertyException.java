package compass.exceptions;

public class NoPropertyException extends Exception {
    public NoPropertyException() {
        super("The specified property has no constant linked to it.");
    }
}
