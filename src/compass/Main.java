package compass;

import compass.exceptions.PropertiesNotLoadedException;
import compass.objects.CompassProperties;
import compass.utilities.NLP;

public class Main {
    private static boolean propertiesLoaded = false;

    public static void main(String[] args) {
        String placeholder = "This method will not be used ever in its entire life." +
                "It's here just to discard errors present in MANIFEST.MF when deploying library.";

        parseUserQuery("What is the nearest train station?");
    }

    /** Main method that loads relevant data (stopwords/identifiers) and makes the initial call to the NLP module
     * @param input the Natural Language Input provided by the user
     * @return the JSON object provided by the APIs present up the call stack
     */
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

        if (Constants.errdebug) {
            System.err.println(res);
        }

        return res;
    }
}
