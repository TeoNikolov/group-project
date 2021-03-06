package compass.objects;

import java.io.*;
import java.util.Properties;

import compass.Constants;
import compass.exceptions.NoPropertyException;
import compass.exceptions.PropertiesNotLoadedException;
import compass.exceptions.PropertyNotFoundException;

// Class responsible for loading Java properties
public class CompassProperties {

    /**
     * Example of using enumerations to handle the type of property (can be expanded on in the future)
     */
    public enum PropertyType {
        WeatherAPIKey,
    }

    private Properties prop;
    private boolean hasCompleted = false;

    public boolean hasCompleted() {
        return hasCompleted;
    }

    public CompassProperties() throws PropertiesNotLoadedException {
        loadProperties();
        if (prop != null)
            hasCompleted = true;
    }

    /** Function loads the properties file with location indicated by Constants 'propFileName'
     * @throws PropertiesNotLoadedException
     */
    private void loadProperties() throws PropertiesNotLoadedException {
        prop = new Properties();
        FileReader reader = null;

        try {
            File propertiesFile = new File(Constants.basePath + Constants.propFileName);
            reader = new FileReader(propertiesFile);
            prop.load(reader);
        } catch (IOException e) {
            prop = null;
            e.printStackTrace();
            throw new PropertiesNotLoadedException();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** Attempts to get the value of a specific property type (custom enumeration)
     * @param prop the property to extract the value of
     * @return the String representation of the value of the specified property
     * @throws PropertyNotFoundException
     * @throws NoPropertyException
     */
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
