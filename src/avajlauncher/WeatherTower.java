package avajlauncher;

import avajlauncher.aircrafts.Coordinates;
import avajlauncher.exceptions.FileWriteException;
import avajlauncher.weather.Tower;
import avajlauncher.weather.WeatherProvider;


public class WeatherTower extends Tower{
    
    public String getWeather(Coordinates coordinates) {
        return WeatherProvider.getProvider().getCurrentWeather(coordinates);
    }
    
    void changeWeather() throws FileWriteException {
        conditionsChanged();
    }
}
