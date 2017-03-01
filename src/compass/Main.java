package compass;

import compass.exceptions.PropertiesNotLoadedException;
import compass.utilities.APIInterface;

public class Main {
    public static void main(String[] args) {
        // Try setting up properties
        try {
            CompassProperties tempprops = new CompassProperties();
            if (tempprops.hasCompleted())
                Constants.props = tempprops;
                Constants.propertiesLoaded = true;
        }  catch (PropertiesNotLoadedException e) {
            e.printStackTrace();
        }

        if (Constants.propertiesLoaded) {
            // Properties have successfully been loaded
            APIInterface.getWeather(null); // <- This method will get the weather from Dark Sky weather API
        }
    }
}
