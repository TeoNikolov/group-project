package compass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import compass.exceptions.NoPropertyException;
import compass.exceptions.PropertiesNotLoadedException;
import compass.exceptions.PropertyNotFoundException;

// Class responsible for loading Java properties
public class CompassProperties {

    public enum PropertyType {
        WeatherAPIKey,
    }

    private Properties prop;
    private boolean hasCompleted = false;

    public boolean hasCompleted() {
        return hasCompleted;
    }

    CompassProperties() throws PropertiesNotLoadedException {
        loadProperties();
        if (prop != null)
            hasCompleted = true;
    }

    // Function loads the properties file with location indicated by Constants 'propFileName'
    private void loadProperties() throws PropertiesNotLoadedException {
        InputStream inputStream;

        prop = new Properties();
        inputStream = getClass().getClassLoader().getResourceAsStream(Constants.propFileName);

        try {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + Constants.propFileName +
                        "' not found in the classpath.");
            }
        } catch (IOException e) {
            prop = null;
            e.printStackTrace();
            throw new PropertiesNotLoadedException();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public String getPropertyValue(PropertyType prop) throws PropertyNotFoundException, NoPropertyException {
        String property = "";

        switch(prop) {
            case WeatherAPIKey:
                property = Constants.propWeatherAPIKey;
                break;
        }

        if (property.equals(""))
            throw new NoPropertyException();

        String result = this.prop.getProperty(property);
        if (result == null) {
            throw new PropertyNotFoundException(property);
        } else {
            return result;
        }
    }
}
