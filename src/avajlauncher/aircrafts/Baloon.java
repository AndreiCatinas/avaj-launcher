package avajlauncher.aircrafts;

import avajlauncher.inputoutput.Output;
import avajlauncher.WeatherTower;
import avajlauncher.exceptions.FileWriteException;

public class Baloon extends Aircraft implements Flyable {

    private WeatherTower weatherTower;

    Baloon(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() throws FileWriteException{
        String weather = weatherTower.getWeather(coordinates);
        if (weather.equals("SUN")) {
            coordinates.setLongitude(coordinates.getLongitude() + 2);
            coordinates.setHeight(coordinates.getHeight() + 4);
            Output.writeConditions(this + " Roses are red, outside it's sunny. You are ugly and this poem ain't funny.\n");
        } else if (weather.equals("RAIN")) {
            coordinates.setHeight(coordinates.getHeight() - 5);
            Output.writeConditions(this + " Rain. Period.\n");
        } else if (weather.equals("FOG")) {
            coordinates.setHeight(coordinates.getHeight() - 3);
            Output.writeConditions(this + " Why would I care if it's foggy? I'm flying... Right?\n");
        } else if (weather.equals("SNOW")) {
            coordinates.setHeight(coordinates.getHeight() - 15);
            Output.writeConditions(this + " It's snowing and my baloon is deflating. Also, skyrim belongs to the nords.\n");
        }
        if (coordinates.getHeight() > 100) {
            coordinates.setHeight(100);
        }
        if (coordinates.getHeight() <= 0) {
            coordinates.setHeight(0);
            weatherTower.unRegister(this);
        }
    }
    

    @Override
    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
    }
}
