package avajlauncher;

import avajlauncher.inputoutput.*;
import avajlauncher.aircrafts.Flyable;
import avajlauncher.exceptions.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulator {

    public static void main(String[] args) throws Exception {
        Md5Converter.writeMd5("src/avajlauncher/scenario.txt", "src/avajlauncher/scenario.md5");  // <-- if you want to convert the scenario.txt into a md5 scenario.md5  

        try {
            Output out = new Output("src/avajlauncher/simulation.txt");
        } catch (FileCreateException e) {
            System.err.print(e);
            System.exit(1);
        }

        WeatherTower tower = new WeatherTower();

        try {
            Input in = new Input(args[0], tower);

            for (int i = 0; i < in.getNbOfSimulations(); i++) {
                tower.changeWeather();
            }
        } catch (FileReadException | FileFormatException e) {
            System.err.print(e);
            System.exit(1);
        } catch (FileWriteException e) {
            System.out.println("Cannot write to file " + e.toString());
            System.exit(1);
        }
    }
}
