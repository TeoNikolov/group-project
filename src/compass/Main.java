package compass;

import compass.exceptions.PropertiesNotLoadedException;
import compass.utilities.APIInterface;

public class Main {
    private static boolean propertiesLoaded = false;

    public static void main(String[] args) {
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
            // Properties have successfully been loaded
            APIInterface.getWeather(null); // <- This method will get the weather from Dark Sky weather API
        }
    }
}
