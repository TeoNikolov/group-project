package compass;

import compass.exceptions.PropertiesNotLoadedException;
import compass.utilities.APIInterface;

public class Main {
    private static boolean propertiesLoaded = false;

    public static void main(String[] args) throws Exception {
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
            
        	//Gets weather
        	APIInterface.getAPI("Weather", "London");
        	
        	//Gets transport for Cardiff
        	APIInterface.getAPI("Transport", "Cardiff");
        	
        	//Gets transport for Cardiff to London (currently not working
        	//APIInterface.getAPI("Transport", "Cardiff-London");
        	
        	//Accesses DBPedia
        	APIInterface.getAPI("General", "Cardiff Castle");
        	
        	//Accesses DBPedia
        	APIInterface.getAPI("General", "Cathays");
        	
        }
    }
}
