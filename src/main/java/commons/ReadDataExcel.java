package commons;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadDataExcel {
    public void readExcel(String filePath, String fileName, String sheetName) throws IOException {
        //Create an object of File class to open xlsx file
        File file = new File(filePath + "\\" + fileName);

        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        Workbook shipperIDDoc = null;

        //Find the file extension by splitting file name in substring  and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file
        if (fileExtensionName.equals(".xlsx")) {
            //If it is xlsx file then create object of XSSFWorkbook class
            shipperIDDoc = new XSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else if (fileExtensionName.equals(".xls")) {
            //if it is xls file then create object of HSSWorkbook class
            shipperIDDoc = new HSSFWorkbook(inputStream);
        }

        //Read sheet inside the workbook by its name
        Sheet shipperIDSheet = shipperIDDoc.getSheet(sheetName);

        //Find number of rows in excel file
        int rowCount = shipperIDSheet.getLastRowNum()-shipperIDSheet.getFirstRowNum();
        System.out.println("Total rows: " + rowCount);

        //Create a loop over all the rows of excel file to read it
        for(int i = 0; i < rowCount+1; i++){
            Row row = shipperIDSheet.getRow(i);

            //Create a loop to print cell values in a row
            for (int j = 0; j < row.getLastCellNum(); j++){
                //Print excel data in console
                System.out.println(row.getCell(j).getStringCellValue()+"|| ");
            }
            System.out.println();
        }

    }
}
