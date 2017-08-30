package avajlauncher.weather;

import avajlauncher.inputoutput.Output;
import avajlauncher.aircrafts.Flyable;
import avajlauncher.exceptions.FileWriteException;
import java.util.*;

public abstract class Tower {

    private ArrayList<Flyable> observers;
    private ArrayList<Flyable> landed;

    public Tower() {
        observers = new ArrayList<>();
        landed = new ArrayList<>();
    }

    public void register(Flyable flyable) throws FileWriteException{
        Output.writeConditions("Tower says: " + flyable + " registered to weather tower.\n");
        observers.add(flyable);
    }

    public void unRegister(Flyable flyable) throws FileWriteException{
        Output.writeConditions(flyable + " landing\n");
        Output.writeConditions("Tower says: " + flyable + " unregistered from weather tower\n");
        //creates a temporary list of Flyable objects to be unregistered(deleted) from the tower
        landed.add(flyable);
    }

    protected void conditionsChanged() throws FileWriteException{
        for (Flyable f : observers) {
            f.updateConditions();
        }
        //delets the unregistered Flyables
        for (Flyable l : landed) {
            observers.remove(l);
        }
        //removes the unregistered Flyables from the temporary list
        landed = new ArrayList<>();
    }
}
