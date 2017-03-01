package compass.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class APIInterface {

    // Returns formatted weather data (in HTML)
    // Pass it relevant input (will need to be processed by NLP first)
    public static String getWeather(JSONObject args) {
        JSONParser parser = new JSONParser();
        Object obj;

        // Try getting weather information from Weather API
        try {
            obj = parser.parse(Collector.collectWeatherData(null)); // Some arguments for collecting weather info (JSON)
            if (obj == null) // Make sure our collected data is not null (due to any issues)
                return "";
            if (obj.equals("")) // Make sure our data is not empty before proceeding
                return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        JSONObject jsonObject = (JSONObject) obj;

        System.out.println("Weather Data:\n" + jsonObject);

        // If database needs to be updated, put relevant code here
        boolean DBNeedsUpdating = true;
        if (DBNeedsUpdating) {
            String storingQuery = Storing.genWeatherQuery(null); // Some arguments for generating "storing" query (JSON)
            Querying.sendQuery(storingQuery);
        }

        // Obtain information from database, format it and return an HTML string
        String query = Querying.generateWeatherQuery(null); // Some arguments for generating "retrieve" query (JSON)
        String result = Querying.sendQuery(query);
        return HTMLFormatter.formatWeather(jsonObject); // Return the formatted HTML data
    }
}
