package avajlauncher.aircrafts;

import avajlauncher.inputoutput.Output;
import avajlauncher.WeatherTower;
import avajlauncher.exceptions.FileWriteException;

public class Helicopter extends Aircraft implements Flyable {

    private WeatherTower weatherTower;

    Helicopter(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() throws FileWriteException{
        String weather = weatherTower.getWeather(coordinates);
        if (weather.equals("SUN")) {
            coordinates.setLongitude(coordinates.getLongitude() + 10);
            coordinates.setHeight(coordinates.getHeight() + 2);
            Output.writeConditions(this + " Who needs air conditioning when you have a big ass fan above you?\n");
        } else if (weather.equals("RAIN")) {
            coordinates.setLongitude(coordinates.getLongitude() + 5);
            Output.writeConditions(this + " Finally, some rain! Wait, is my laundry still on the balcony? DAMN IT!\n");
        } else if (weather.equals("FOG")) {
            coordinates.setLongitude(coordinates.getLongitude() + 1);
           Output.writeConditions(this + " Fog? I don't want fog. Run the weather generator again!\n");
        } else if (weather.equals("SNOW")) {
            coordinates.setHeight(coordinates.getHeight() - 12);
            Output.writeConditions(this + " Wow. The dude with the jetplane was right. IT IS snowing!\n");
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
