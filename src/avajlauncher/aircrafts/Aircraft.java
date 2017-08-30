package avajlauncher.aircrafts;

import avajlauncher.aircrafts.Coordinates;

public abstract class Aircraft {

    protected long id;
    protected String name;
    protected Coordinates coordinates;
    private static long idCounter;

    protected Aircraft(String name, Coordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
        this.id = nextId();
    }

    private long nextId() {
        return ++idCounter;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ")";
        //+  coordinates.getLongitude() + " " + coordinates.getLatitude() + " " + coordinates.getHeight();
    }
}
