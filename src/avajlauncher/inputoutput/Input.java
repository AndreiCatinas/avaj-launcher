package avajlauncher.inputoutput;

import avajlauncher.WeatherTower;
import avajlauncher.aircrafts.*;
import avajlauncher.exceptions.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;

// This class reads the input file, creates the Aircraft objects and then passes them to the WeatherTower Object.
public class Input {

    private WeatherTower tower;
    private int nbOfSimulations;
    private String path;
    private File simulation;
    private ArrayList<String> scenarioFile;

    public Input(String path, WeatherTower tower) throws FileReadException, FileFormatException, FileWriteException {
        scenarioFile = new ArrayList<>();
        this.path = path;
        this.tower = tower;
        readFile();
        if (path.endsWith("txt")) {
            plainTextFile();
        } else if (path.endsWith("md5")) {
            md5File();
        } else {
            System.out.println("Wrong input file.");
            System.exit(1);
        }
    }

    private void readFile() throws FileReadException, FileFormatException, FileWriteException {
        try {
            //if the output file exists, it deletes it.
            File file = new File("src/avajlauncher/simulation.txt");
            if (file.exists()) {
                file.delete();
            }
            BufferedReader scenario = new BufferedReader(new FileReader(path));
            String line;
            while ((line = scenario.readLine()) != null) {
                scenarioFile.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new FileReadException("Make sure file " + path + " exists: ");
        } catch (IOException e) {
            throw new FileReadException("Cannot read file: " + path);
        }
    }

    private void plainTextFile() throws FileFormatException, FileWriteException {
        if (!scenarioFile.get(0).matches("^(0-9]|[1-9][0-9]|[1-9][0-9][0-9])$")) {
            throw new FileFormatException("Your input file either asks for a ridiculous amount of simulations, doesn't have a number of sumulations, or is not a number. Fix it!");
        } else {
            this.nbOfSimulations = Integer.parseInt(scenarioFile.get(0));
        }
        for (String line : scenarioFile) {
            if (line == scenarioFile.get(0)) {
                continue;
            }
            String[] split = line.split(" ");
            if (!split[0].matches("^(Helicopter|Baloon|JetPlane)$")) {
                throw new FileFormatException("I'm expecting some baloons, Helicopters or Jetplanes. You're giving me something else. Check input file.");
            }
            if (!split[1].matches("^(B|H|J)([1-9]|[1-9][0-9])$")) {
                throw new FileFormatException("You may have a problem with the aicraft names in input file. That's unacceptable young man/woman/it.");
            }
            if (!split[2].matches("^([0-9]|[1-9][0-9]|[1-3][0-5][0-9]|360)$")) {
                throw new FileFormatException("Make sure longitudes range from 0 to 360 in input file.");
            }
            if (!split[3].matches("^([0-9]|[1-9][0-9]|1[0-7][0-9]|180)$")) {
                throw new FileFormatException("I'm gonna say the latitude can't be less than 0 or more than 180. I mean, it makes sense right? Go to input file.");
            }
            if (!split[4].matches("^(0-9]|[1-9][0-9]|100)$")) {
                throw new FileFormatException("Height must be between 0 and 100. Make sure it is. In the input file!");
            }

            Flyable thisFlyable = AircraftFactory.newAircraft(split[0],
                    split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]),
                    Integer.parseInt(split[4]));

            thisFlyable.registerTower(tower);
            tower.register(thisFlyable);
        }
    }

    private void md5File() throws FileFormatException, FileWriteException {
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i <= 999; i++) {
            if ((scenarioFile.get(0).equals(encode(i + "")))) {
                found.add(i + "");
                break;
            }
        }
        int b = 1;
        int h = 1;
        int j = 1;
        for (String line : scenarioFile) {
            if (line == scenarioFile.get(0)) {
                continue;
            }
            outerloop:
            for (int lg = 0; lg <= 360; lg++) {
                for (int lt = 0; lt <= 180; lt++) {
                    for (int hg = 0; hg <= 100; hg++) {
                        String tmpMD5 = null;
                        String tmpBaloon = "Baloon B" + b + " " + lg + " " + lt + " " + hg;
                        String tmpHelicopter = "Helicopter H" + h + " " + lg + " " + lt + " " + hg;
                        String tmpJetPlane = "JetPlane J" + j + " " + lg + " " + lt + " " + hg;
                        if ((tmpMD5 = encode(tmpBaloon)).equals(line)) {
                            found.add(tmpBaloon);
                            b++;
                            break outerloop;
                        } else if ((tmpMD5 = encode(tmpHelicopter)).equals(line)) {
                            found.add(tmpHelicopter);
                            h++;
                            break outerloop;
                        } else if ((tmpMD5 = encode(tmpJetPlane)).equals(line)) {
                            found.add(tmpJetPlane);
                            j++;
                            break outerloop;
                        }
                    }
                }
            }
        }
        this.scenarioFile = found;
        plainTextFile();
    }

    private static String encode(String string) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] digest = md.digest();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return sb.toString();
    }

    public int getNbOfSimulations() {
        return this.nbOfSimulations;
    }
}
