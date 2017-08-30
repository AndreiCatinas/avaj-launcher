package avajlauncher.aircrafts;

import avajlauncher.WeatherTower;
import avajlauncher.exceptions.FileWriteException;

public interface Flyable {

    public void updateConditions() throws FileWriteException;

    public void registerTower(WeatherTower weatherTower);

}
