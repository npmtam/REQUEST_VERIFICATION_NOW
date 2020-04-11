package pageObjects;

import commons.Constants;
import commons.ReadDataExcel;
import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageUIs.RequestsPageUIs;

import java.io.IOException;
import java.util.List;

public class RequestPO extends AbstractPage {
    WebDriver driver;


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
    }

    public void clickToDeclineAllRequest() {
        String text = getTextElement(RequestsPageUIs.NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL);
        if (!text.startsWith("0")) {
            if (isElementPresentInDOM(RequestsPageUIs.DECLINE_ALL)) {
                waitToElementClickable(RequestsPageUIs.DECLINE_ALL);
                clickToElement(RequestsPageUIs.DECLINE_ALL);
            }
        } else {
            System.out.println("There's no request without answer");
        }
    }

    public void clickToConfirmButton() {
        if (isElementPresentInDOM(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP)) {
            waitToElementClickable(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP);
            clickToElement(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP);
        }
    }

    public String getNumberOfRequestNotAnswer() {
        if (isElementPresentInDOM(RequestsPageUIs.NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL)) {
            String countRequestLabel = getTextElement(RequestsPageUIs.NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL);
            String numberOfRequest = countRequestLabel.substring(0, 3);
            return numberOfRequest;
        } else {
            String countRequestLabel = getTextElement(RequestsPageUIs.NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL_BACKUP);
            String numberOfRequest = countRequestLabel.substring(0, 3);
            return numberOfRequest;
        }

    }

    public void clickToRemoveFilter() {
        waitToElementVisible(RequestsPageUIs.REMOVE_FILTER_LINK);
        clickToElement(RequestsPageUIs.REMOVE_FILTER_LINK);
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
        scrollToElement(RequestsPageUIs.REQUESTS_TEXT);
        sleepInSecond(1);
    }

    public void listAndCheckIDRequested() {
        List<WebElement> allID = driver.findElements(By.xpath(RequestsPageUIs.LIST_ID_INPUTTED));
        for (WebElement id : allID) {
            scrollToElement(id);
            String requestID = id.getText();
            System.out.println(requestID);
            int idSize = requestID.length();
            if (!Character.isDigit(requestID.charAt(requestID.length() - 1))) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Decline ID: " + requestID + " - ID không đúng - Ký tự sau cùng");
                System.out.println("-----------------------------------------------------------------");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElementByJS(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            } else if (idSize > 8) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("WARNING: ID " + requestID + " is > 8 characters");
                System.out.println("-----------------------------------------------------------------");
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
            }
        }
//        System.out.println("=================================================================");
    }

    public void pressEndKeyboard() {
        Actions action = new Actions(driver);
        action.keyDown(Keys.END).keyUp(Keys.END).perform();
    }

    public void readAndHandleData(List<String> id) {
        for (String requestID : id) {
            Constants.REQUEST_ID = id.get(0);
            Constants.STATUS = id.get(1);

            scrollToElement(RequestsPageUIs.REQUESTS_TEXT);
            if (Constants.STATUS.equals("OK")) {
                if (isElementPresentInDOM(RequestsPageUIs.APPROVE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID)) {
                    scrollToElement(RequestsPageUIs.APPROVE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID);
                    sleepInSecond(1);
                    waitToElementClickable(RequestsPageUIs.APPROVE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID);
                    clickToElement(RequestsPageUIs.APPROVE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID);
                    System.out.println("---- Approve ID: " + Constants.REQUEST_ID);
                } else {
                    System.out.println("---- The ID " + Constants.REQUEST_ID + " is not presented ----          ||");
                }
            } else if (Constants.STATUS.equals("DECLINE") || Constants.STATUS.equals("DUPLICATED")) {
                if (isElementPresentInDOM(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID)) {
                    scrollToElement(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID);
                    sleepInSecond(1);
                    waitToElementClickable(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID);
                    clickToElement(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, Constants.REQUEST_ID);
                    System.out.println("---- Decline ID: " + Constants.REQUEST_ID);
                } else {
                    System.out.println("---- The ID " + Constants.REQUEST_ID + " is not presented ----          ||");
                }
            }
        }
    }
}
