package avajlauncher.aircrafts;

import avajlauncher.aircrafts.*;

public abstract class AircraftFactory {

    public static Flyable newAircraft(String type, String name, int longitude, int latitude, int height) {
        if (type.equalsIgnoreCase("HELICOPTER")) {
            return new Helicopter(name, new Coordinates(longitude, latitude, height));
        } else if (type.equalsIgnoreCase("JETPLANE")) {
            return new JetPlane(name, new Coordinates(longitude, latitude, height));
        } else if (type.equalsIgnoreCase("BALOON")) {
            return new Baloon(name, new Coordinates(longitude, latitude, height));
        }
        return null;
    }
}
