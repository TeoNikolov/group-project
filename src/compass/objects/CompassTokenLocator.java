package compass.objects;

import java.util.ArrayList;

public class CompassTokenLocator {
    public static boolean exists(ArrayList<CompassToken> tokens, String word) {
        for (CompassToken token : tokens) {
            if (token.token.equals(word)) {
                return true;
            }
        }

        return false;
    }

    public static boolean exists(ArrayList<CompassToken> tokens, String word, String pos) {
        for (CompassToken token : tokens) {
            if (token.token.equals(word)) {
                return true;
            }
        }

        return false;
    }
}
