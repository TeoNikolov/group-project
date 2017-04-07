package compass.exceptions;

public class PropertyNotFoundException extends Exception {

    public PropertyNotFoundException(String property) {
        super(property);
    }
}
