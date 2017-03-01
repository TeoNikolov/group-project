package compass.utilities;

import org.json.simple.JSONObject;

// Class responsible for Storing *collected* data in database
class Storing {

    // Method that generates query and stores data in the database
    public static String genWeatherQuery(JSONObject data) {
        // First format data to fit to database schema
        String formattedData = DBFormatter.formatWeather(data); // Make sure to include relevant parameters
        // Then generate SQL query using JAVA and SQL
        return "Generated Query";
    }
}
