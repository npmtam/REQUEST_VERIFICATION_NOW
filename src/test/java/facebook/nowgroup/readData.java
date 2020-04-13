package facebook.nowgroup;

import commons.ReadDataExcel;

import java.io.IOException;

public class readData {
    public static void main(String...strings) throws IOException {
        ReadDataExcel objExcelFile = new ReadDataExcel();

        String filepath = System.getProperty("user.dir") + "\\src\\main\\resources";
        objExcelFile.readExcel(filepath, "ShipperID.xlsx", "Sheet1");
    }
}
