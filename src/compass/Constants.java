package compass;

import compass.objects.CompassProperties;

import java.io.File;

public class Constants {

    /* Server Error Codes:
    1 - Could not fetch Forecast information
    */

    public static Double cardiffLongitude = -3.179090;
    public static Double cardiffLatitude = 51.481583;

    // Windows Paths
//    public static String basePath = new File("").getAbsolutePath();
//    public static String questionsName = "/src/resources/trainers.txt";
//    public static String stopwordsFilename = "/src/resources/stopwords.txt";
//    public static String idsFilename = "/src/resources/identifiers.txt";
//    public static String propFileName = "/src/resources/config.properties";

    // Apache Paths
    public static String basePath = new File("").getAbsolutePath();
    public static String questionsName = "/../webapps/ROOT/WEB-INF/resources/trainers.txt";
    public static String stopwordsFilename = "/../webapps/ROOT/WEB-INF/resources/stopwords.txt";
    public static String idsFilename = "/../webapps/ROOT/WEB-INF/resources/identifiers.txt";
    public static String propFileName = "/../webapps/ROOT/WEB-INF/resources/config.properties";

    // Properties
    public static Boolean debug = false;
    public static CompassProperties props;
    public static String propWeatherAPIKey = "weatherAPIKey";

    
    //Update times
    public static long weatherUpdateTime = 3600000;		//60 minutes
    public static long transportUpdateTime = 3600000;	//60 minutes
    public static long eventsUpdateTime = 3600000;		//60 minutes
    public static long generalUpdateTime = 86400000;	//24 hours
}
