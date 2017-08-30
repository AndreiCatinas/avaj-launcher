package avajlauncher.inputoutput;

import avajlauncher.exceptions.FileReadException;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

//use this to convert a scenario.txt input file to md5. For testing purposes only

public class Md5Converter {

    private static ArrayList<String> plainTextFile = new ArrayList<>();

    //plainPath is the scenario.txt file you want to convert and md5Path is where you want your md5 file to be, and what to be called
    public static void writeMd5(String plainPath, String md5Path) {
        readFile(plainPath);
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File scenario = new File(md5Path);
            if (!scenario.exists()) {
                scenario.createNewFile();
            }
            fw = new FileWriter(scenario.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            for (String line : plainTextFile) {
                bw.write(encode(line) + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
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

    private static void readFile(String path) {
        try {
            BufferedReader scenario = new BufferedReader(new FileReader(path));
            String line;
            while ((line = scenario.readLine()) != null) {
                plainTextFile.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.toString());
        }
    }

    private static String encode(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
