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

    /** Obtains weather data from Dark Sky services
     * @return the weather information in JSON format
     * @throws PropertyNotFoundException
     * @throws NoPropertyException
     */
    public static String collectWeatherData() throws PropertyNotFoundException, NoPropertyException {
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
                .units(Units.uk2)
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
