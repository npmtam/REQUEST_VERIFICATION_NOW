package pageObjects;

import commons.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageUIs.RequestsPageUIs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestPO extends AbstractPage {
    WebDriver driver;
    String rootFolder = System.getProperty("user.dir");
    String filePath = rootFolder + "/src/test/resources/WriteShipperID.csv";
    String requestID, statusChecked;
    ReadData readDataClass = new ReadData();
    ConnectDB connectDB = new ConnectDB();
    SpreadSheetInteraction spreadSheet = new SpreadSheetInteraction();


    public RequestPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void clickToQuestionFilter() {
        waitToElementVisible(RequestsPageUIs.QUESTION_FILTER_BUTTON);
        clickToElement(RequestsPageUIs.QUESTION_FILTER_BUTTON);
    }

    public void clickToNoAnswerQuestionFilter() {
        waitToElementVisible(RequestsPageUIs.DIDNT_ANSWER_THE_QUESTION_OPTION_LINK);
        clickToElement(RequestsPageUIs.DIDNT_ANSWER_THE_QUESTION_OPTION_LINK);
        waitToElementVisible(RequestsPageUIs.CLEAR_FILTER_DIDNT_ANSWER_QUESTION);
    }

    public boolean isThereTheResult() {
        return isElementPresentInDOM(RequestsPageUIs.NO_RESULT);
    }

    public void clickToDeclineAllRequest() {
        if (isElementPresentInDOM(RequestsPageUIs.DECLINE_ALL)) {
            waitToElementClickable(RequestsPageUIs.DECLINE_ALL);
            clickToElement(RequestsPageUIs.DECLINE_ALL);
        } else {
            System.out.println("There's no request without answer");
        }
    }

    public void clickToConfirmButton() {
        if (isElementDisplayed(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP)) {
//            waitToElementClickable(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP);
            clickToElement(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP);
        }
    }

    public String getNumberOfRequestNotAnswer() {
        String countRequest;
        if (isElementPresentInDOM(RequestsPageUIs.NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL)) {
            countRequest = getTextElement(RequestsPageUIs.NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL);
        } else {
            System.out.println("There's no request without answered");
            countRequest = "0";
        }
        return countRequest;
    }

    public void clickToRemoveFilter() {
        waitToElementVisible(RequestsPageUIs.CLEAR_FILTER_DIDNT_ANSWER_QUESTION);
        clickToElement(RequestsPageUIs.CLEAR_FILTER_DIDNT_ANSWER_QUESTION);
    }

    public boolean isRequestEquals(int expectedResult) {
        return countElements(RequestsPageUIs.REQUEST_PRESENT) == expectedResult;
    }

    public void selectSortSubOptions(String subOptions) {
        waitToElementClickable(RequestsPageUIs.SORT_DROPDOWN_LINK);
        clickToElement(RequestsPageUIs.SORT_DROPDOWN_LINK);
        sleepInSecond(1);

        waitToElementVisible(RequestsPageUIs.SORT_SUBOPTIONS_LINK, subOptions);
        clickToElement(RequestsPageUIs.SORT_SUBOPTIONS_LINK, subOptions);
    }

    public boolean isOldestSortWasSelected() {
        return isElementPresentInDOM(RequestsPageUIs.OLDEST_OPTIONS_SELECTED);
    }

    public void scrollToLoadID() {
        scrollToLoadMore();
        sleepInSecond(3);
        scrollToElement(RequestsPageUIs.MATCHING_REQUESTS_TEXT);
        sleepInSecond(1);
    }

    public void listAndCheckIDRequested() {
        List<WebElement> allID = driver.findElements(By.xpath(RequestsPageUIs.LIST_ID_INPUTTED));
        for (WebElement id : allID) {
            scrollToElement(id);
            requestID = id.getText();
            System.out.println(requestID);
            int idSize = requestID.length();
            if (!Character.isDigit(requestID.charAt(requestID.length() - 1))) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Decline ID: " + requestID + " - ID không đúng - Ký tự sau cùng");
                System.out.println("-----------------------------------------------------------------");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElementByJS(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            } else if (idSize > 12) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Decline ID: " + requestID + " due to > 12 characters");
                System.out.println("-----------------------------------------------------------------");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElementByJS(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            } else if (idSize <= 6) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Decline ID: " + requestID + " due to < 6 characters");
                System.out.println("-----------------------------------------------------------------");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElementByJS(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            } else if (requestID.startsWith("0")) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Decline ID: " + requestID + "due to start with 0");
                System.out.println("-----------------------------------------------------------------");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElementByJS(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            } else {
//                writeDataToCsv(requestID, Constants.NEED_CONFIRMATION);
                try {
                    connectDB.selectIDFromDB(requestID);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                connectDB.insertRecordToDB(requestID, Constants.REQUEST_STATUS, Constants.COMMENTS);
                spreadSheet.appendDataToSpreadSheet(requestID, Constants.REQUEST_STATUS, Constants.COMMENTS);
            }
        }
    }


    public void pressEndKeyboard() {
        Actions action = new Actions(driver);
        action.keyDown(Keys.END).keyUp(Keys.END).perform();
    }

    public void clickToAcceptID(String acceptID) {
        waitToElementClickable(RequestsPageUIs.APPROVE_BUTTON_FOR_EACH_ID, acceptID);
        clickToElementByJS(RequestsPageUIs.APPROVE_BUTTON_FOR_EACH_ID, acceptID);
    }

    public void clickToDeclineID(String declineID) {
        waitToElementClickable(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, declineID);
        clickToElementByJS(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, declineID);
    }

    public void readAndHandleData(List<String> id) {
        Constants.REQUEST_ID = id.get(0);
        Constants.STATUS = id.get(1);
//        driver.get(Constants.URL);
//        scrollToElement(RequestsPageUIs.REQUESTS_TEXT);
        if (Constants.STATUS.equals("OK")) {
            if (isElementPresentInDOM(RequestsPageUIs.APPROVE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID)) {
                scrollToElement(RequestsPageUIs.APPROVE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID);
//                sleepInSecond(1);
                clickToAcceptID(Constants.REQUEST_ID);
                System.out.println("---- Approve ID: " + Constants.REQUEST_ID);
            } else {
                System.out.println("---- The ID " + Constants.REQUEST_ID + " is not presented ----          ||");
            }
        } else if (Constants.STATUS.equals("DECLINE") || Constants.STATUS.equals("DUPLICATED")) {
            if (isElementPresentInDOM(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID)) {
                scrollToElement(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID);
                sleepInSecond(1);
                clickToDeclineID(Constants.REQUEST_ID);
                System.out.println("---- Decline ID: " + Constants.REQUEST_ID);
            } else {
                System.out.println("---- The ID " + Constants.REQUEST_ID + " is not presented ----          ||");
            }
        }
    }

    public void readAndHandleRequestID() {
        BufferedReader reader = null;
        try {
            String line;
            reader = new BufferedReader(new FileReader(Constants.FILE_PATH));

            System.out.println("======================================================");
            //Read file in java line by line
            while ((line = reader.readLine()) != null) {
                while ((line = reader.readLine()) != null) {
                    readAndHandleData(readDataClass.parseCsvLine(line));
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


    public void writeDataToCsv(String idRequest, String status) {
        //Create new data object
        IDRequested data = new IDRequested(idRequest, status);

        List<IDRequested> idRequestList = new ArrayList<>();
        idRequestList.add(data);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, true);
            for (IDRequested id : idRequestList) {
                fileWriter.append(id.getId());
                fileWriter.append(Constants.COMMA_DELIMITER);
                fileWriter.append(id.getStatus());
            }
            fileWriter.append(Constants.NEW_LINE_SEPARATOR);
        } catch (Exception e) {
            System.out.println("Error in CSVFileWriter");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter");
                e.printStackTrace();
            }
        }
    }
}
