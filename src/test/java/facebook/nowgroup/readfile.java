package facebook.nowgroup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class readfile {
    String email, password;
    private static final String COMMA_DELIMITER = ","; // Split by comma


    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader("C:\\Attachments\\RequestID.csv"));

            // How to read file in java line by line?
            while ((line = br.readLine()) != null) {
                while ((line = br.readLine()) != null) {
                    printContry(parseCsvLine(line));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
    }

    public static List<String> parseCsvLine(String csvLine) {
        List<String> result = new ArrayList<String>();
        if (csvLine != null) {
            String[] splitData = csvLine.split(COMMA_DELIMITER);
            for (int i = 0; i < splitData.length; i++) {
                result.add(splitData[i]);
            }
        }
        return result;
    }

    private static void printContry(List<String> country) {
        System.out.println(
                        "(" + "'"+ country.get(0) + "'" + ","
                        + "'" + country.get(1) +"'" + "," + "'" + "'" + ")" + ",");
    }
}
