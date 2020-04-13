package commons;

import org.openqa.selenium.WebDriver;
import pageObjects.RequestPO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadData {
    static RequestPO requestPage;
    static WebDriver driver;
    public static final String COMMA_DELIMITER = ",";

    public ReadData(WebDriver driver) {
        this.driver = driver;
    }

    public static void readData() {
        BufferedReader reader = null;
        requestPage = new RequestPO(driver);
        try {
            String line;
            reader = new BufferedReader(new FileReader(Constants.FILE_PATH));

            System.out.println("======================================================");
            //Read file in java line by line
            while ((line = reader.readLine()) != null) {
                while ((line = reader.readLine()) != null) {
                    requestPage.readAndHandleData(parseCsvLine(line));
                }
            }
            System.out.println("======================================================");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
    }

    private static List<String> parseCsvLine(String csvLine) {
        List<String> result = new ArrayList<String>();
        if (csvLine != null) {
            String[] splitData = csvLine.split(Constants.COMMA_DELIMITER);
            for (int i = 0; i < splitData.length; i++) {
                result.add(splitData[i]);
            }
        }
        return result;
    }

//    private static void writeData(List<String> id) {
//        for (String requestID : id) {
//            Constants.REQUEST_ID = id.get(0);
//            Constants.STATUS = id.get(1);
//            System.out.println(Constants.REQUEST_ID);
//            System.out.println(Constants.STATUS);
//        }
//    }
}