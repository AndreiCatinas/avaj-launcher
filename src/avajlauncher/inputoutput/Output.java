package avajlauncher.inputoutput;

import java.io.*;
import avajlauncher.exceptions.*;

//
public class Output {

    private static File simulation;

    public Output(String path) throws FileCreateException{
        try {
            Output.simulation = new File(path);
            if (!Output.simulation.exists()) {
                Output.simulation.createNewFile();
            }
        } catch (Exception e) {
            throw new FileCreateException("Problem creating file " + path + "\n");
        }
    }

    //static method for writing the output to file
    public static void writeConditions(String string) throws FileWriteException{
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(Output.simulation.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(string);
        } catch (Exception e) {
            throw new FileWriteException("Can't write to file " + simulation.getAbsolutePath() + "\n");
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
