package compass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Class responsible for loading Java properties
public class CompassProperties {
    private Properties prop;

    CompassProperties() {
        try {
            loadProperties();
        } catch (IOException e) {
            System.out.println("Unable to load properties file. Please check file is located in the correct folder " +
                    "(as indicated in Constants class).");
        }
    }

    /**
     * Function loads the properties file with location indicated by Constants 'propFileName'.
     * */
    private void loadProperties() throws IOException {
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
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
    }

    public String getWeatherAPIKey() throws NullPointerException {
        if (prop != null) {
            return prop.getProperty(Constants.propWeatherAPIKey);
        } else {
            throw new NullPointerException();
        }
    }
}
