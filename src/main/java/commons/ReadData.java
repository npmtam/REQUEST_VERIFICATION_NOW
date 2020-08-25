package commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadData {
    public List<String> parseCsvLine(String csvLine) {
        List<String> result = new ArrayList<String>();
        if (csvLine != null) {
            String[] splitData = csvLine.split(Constants.COMMA_DELIMITER);
            for (int i = 0; i < splitData.length; i++) {
                result.add(splitData[i]);
            }
        }
        return result;
    }

    public void readAccountInfo(String fileName){
        try {
            File myObj = new File("C:\\Attachments\\" + fileName);
            Scanner myReader = new Scanner(myObj);
            Constants.EMAIL = myReader.nextLine();
            Constants.PASSWORD = myReader.nextLine();
            System.out.println("Email: " + Constants.EMAIL);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}