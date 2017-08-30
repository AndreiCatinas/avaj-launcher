package avajlauncher.inputoutput;

import avajlauncher.exceptions.FileCreateException;
import avajlauncher.exceptions.FileWriteException;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//this class is used for generating a dictionary aiding in decoding the md5 input file
//use with caution as it has a pretty high running time and the resulted file exceeds 5 GB

public class DictionaryGenerator {

    private static final String[] aircraft = {"Baloon B", "Helicopter H", "JetPlane J"};
    private static final String path = "src/avajlauncher/inputoutput/dictionary.txt";

    //the parameters represent limits for the generation of your dictionary. 
    //For example if you want the index to go up to 10, longitude up to 360, latitude up to 180 and height up to 100 use "DictionaryGenerator.generate(10, 360, 180, 100)";
    public static void generate(int index, int longitude, int latitude, int height) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File dictionary = new File(path);
            if (!dictionary.exists()) {
                dictionary.createNewFile();
            }
            fw = new FileWriter(dictionary.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            String plain = null;
            String md5 = null;

            for (String ac : aircraft) {
                for (int i = 1; i < index; i++) {
                    for (int j = 0; j <= longitude; j++) {
                        for (int k = 0; k <= latitude; k++) {
                            for (int h = 0; h <= height; h++) {
                                plain = ac + i + " " + j + " " + k + " " + h;
                                md5 = encode(plain);
                                bw.write(md5 + "==" + plain + "\n");
                            }
                        }
                    }
                }
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
}
