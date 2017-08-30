package avajlauncher.weather;

import avajlauncher.aircrafts.Coordinates;
import java.util.concurrent.ThreadLocalRandom;

public class WeatherProvider {

    private static WeatherProvider weatherProvider;
    private final String[] weather = {"SUN", "RAIN", "FOG", "SNOW"};

    private WeatherProvider() {

    }

    public static WeatherProvider getProvider() {
        if (weatherProvider == null) {
            weatherProvider = new WeatherProvider();
        }
        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        int random = ThreadLocalRandom.current().
                nextInt((coordinates.getLongitude()/2 + 1 + coordinates.getLatitude()/2),
                        (coordinates.getLongitude() + coordinates.getLatitude() + 2 * coordinates.getHeight()));
        return weather[(random * 31 + coordinates.getHeight()) % 4];
    }
}
