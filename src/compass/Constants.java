package compass;

public class Constants {

    /* Server Error Codes:
    1 - Could not fetch Forecast information
    */

    public static Double cardiffLongitude = -3.179090;
    public static Double cardiffLatitude = 51.481583;

    public static String propFileName = "resources/config.properties";

    // Properties
    public static CompassProperties props;
    public static boolean propertiesLoaded = false;
    public static String propWeatherAPIKey = "weatherAPIKey";
}
