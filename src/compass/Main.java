package compass;

import compass.utilities.APIInterface;

public class Main {
    public static void main(String[] args) {
        APIInterface.getWeather(null); // <- This method will get the weather from Dark Sky weather API
    }
}
