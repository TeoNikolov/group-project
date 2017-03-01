package compass.utilities;

import org.json.simple.JSONObject;

// Class responsible for constructing and sending queries to the database
class Querying {

    // Method that will build the needed query and send it to the database
    // 'args' should be passed and included as relevant data in the query -> Easy to convert in SQL
    // 'args' preferably in JSON format (alternatively, use 'String[] args', and adjust calling method to this format)
    public static String generateWeatherQuery(JSONObject data) {
        return "Generated query goes here";
    }

    public static String sendQuery(String query) {
        // Code that connects to Database and sends query
        return "Return query result here";
    }
}
