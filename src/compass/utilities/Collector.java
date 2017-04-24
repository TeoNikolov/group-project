package compass.utilities;

import compass.objects.CompassProperties.PropertyType;
import compass.Constants;
import compass.exceptions.NoPropertyException;
import compass.exceptions.PropertyNotFoundException;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder.Block;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder.Units;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder.Language;


// Class responsible for Collecting data from API sources
// Data commonly (if not always) obtained in JSON format
class Collector {

    // Obtain weather data from Dark Sky services
    // 'args' currently has no usage. Can be useful to select what data is needed (hourly or not, daily or not, etc.)
    public static String collectWeatherData(String[] args) throws PropertyNotFoundException, NoPropertyException {
        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey(Constants.props.getPropertyValue(PropertyType.WeatherAPIKey)))
                .location(new GeoCoordinates(new Longitude(Constants.cardiffLongitude), new Latitude(Constants.cardiffLatitude)))
                .exclude(
                        Block.flags,
                        Block.alerts,
                        Block.minutely,
                        Block.hourly,
                        Block.daily
                )
                .units(Units.si)
                .language(Language.en).build();

        DarkSkyClient client = new DarkSkyClient();

        try {
            return client.forecastJsonString(request);
        } catch (ForecastException e) {
            e.printStackTrace();
            return "";
        }
    }

}
