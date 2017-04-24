package compass;

import compass.objects.CompassProperties;

import java.io.File;

public class Constants {

    /* Server Error Codes:
    1 - Could not fetch Forecast information
    */

    public static Double cardiffLongitude = -3.179090;
    public static Double cardiffLatitude = 51.481583;

    public static String basePath = new File("").getAbsolutePath();
    public static String questionsName = "/src/resources/trainers.txt";
    public static String stopwordsFilename = "/src/resources/stopwords.txt";
    public static String idsFilename = "/src/resources/identifiers.txt";
    public static String propFileName = "resources/config.properties";

    // Properties
    public static Boolean debug = true;
    public static CompassProperties props;
    public static String propWeatherAPIKey = "weatherAPIKey";

}
