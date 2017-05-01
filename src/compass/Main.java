package compass;

import compass.exceptions.PropertiesNotLoadedException;
import compass.objects.CompassProperties;
import compass.utilities.NLP;

public class Main {
    private static boolean propertiesLoaded = false;

    public static void main(String[] args) {
        String lol = "This method will not be used ever in its entire life." +
                "It's here just to please MANIFEST.MF" +
                "May fix it soon";
    }

    public static String parseUserQuery(String input) {
        NLP.loadIdentifiers();
        NLP.loadStopwords();

        String res = "There was an error on the server and your input could not be parsed.";

        // Try setting up properties
        try {
            CompassProperties tempprops = new CompassProperties();
            if (tempprops.hasCompleted())
                Constants.props = tempprops;
            propertiesLoaded = true;
        }  catch (PropertiesNotLoadedException e) {
            e.printStackTrace();
        }

        if (propertiesLoaded) {
            res = NLP.parseInput(input);
        }

        return res;
    }
}
