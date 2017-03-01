package compass.utilities;

import org.json.simple.JSONObject;

import java.util.Date;

// Class responsible for formatting the resulting data into HTML to pass back to client
class HTMLFormatter {

    public static String formatWeather(JSONObject data) {
        data = (JSONObject) data.get("currently");

        System.out.println("Summary: " + data.get("summary"));
        System.out.println("Date: " + UNIXtoDate((long) data.get("time"))); // In UNIX
        System.out.println("Temperature: " + data.get("temperature") + "°");
        System.out.println("Apparent Temperature: " + data.get("apparentTemperature") + "°");
        System.out.println("Wind Speed: " + data.get("windSpeed") + "ms");
        System.out.println("Wind Bearing: " + data.get("windBearing") + "°");
        System.out.println("Precipitation Probability: " + data.get("precipProbability"));
        System.out.println("Precipitation Type: " + data.get("precipType"));
        System.out.println("Precipitation Intensity: " + data.get("precipIntensity" + "mm"));
        System.out.println("Precipitation Intensity Error: " + data.get("precipIntensityError") + "mm");
        System.out.println("Dew Point: " + data.get("dewPoint") + "°");
        System.out.println("Humidity: " + data.get("humidity") + "%");
        System.out.println("Pressure: " + data.get("pressure") + "mb");
        System.out.println("Visibility: " + data.get("visibility") + "km");
        System.out.println("Cloud Cover: " + data.get("cloudCover"));
        System.out.println("Icon: " + data.get("icon"));
        System.out.println("Ozone: " + data.get("ozone"));
        System.out.println("Nearest Storm Distance: " + data.get("nearestStormDistance") + "km");

        return "<body> Some weather in HTML </body>";
    }

    private static Date UNIXtoDate(long timestamp) {
        return new Date(timestamp*1000);
    }

}
