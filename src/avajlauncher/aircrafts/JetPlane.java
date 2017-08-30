package avajlauncher.aircrafts;

import avajlauncher.inputoutput.Output;
import avajlauncher.WeatherTower;
import avajlauncher.exceptions.FileWriteException;

public class JetPlane extends Aircraft implements Flyable {

    private WeatherTower weatherTower;

    JetPlane(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() throws FileWriteException{
        String weather = weatherTower.getWeather(coordinates);
        if (weather.equals("SUN")) {
            coordinates.setLatitude(coordinates.getLatitude() + 10);
            coordinates.setHeight(coordinates.getHeight() + 2);
            Output.writeConditions(this + " I'm above the skies and it's sunny, go figure!\n");
        } else if (weather.equals("RAIN")) {
            coordinates.setLatitude(coordinates.getLatitude() + 5);
            Output.writeConditions(this + " Oh, it's raining. Good thing this is not a convertable.\n");
        } else if (weather.equals("FOG")) {
            coordinates.setLatitude(coordinates.getLatitude() + 1);
            Output.writeConditions(this + " Can't see a damn thing through all this fog. Is that a mount...\n");
        } else if (weather.equals("SNOW")) {
            coordinates.setHeight(coordinates.getHeight() - 7);
            Output.writeConditions(this + " Hey! It's snowing. Better post on facebook to let everyone know.\n");
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
