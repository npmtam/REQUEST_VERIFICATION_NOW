package pageObjects;

import com.google.inject.Key;
import com.google.inject.internal.cglib.core.$AbstractClassGenerator;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageUIs.RequestsPageUIs;
import sun.misc.Request;

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
        if (isElementPresentInDOM(RequestsPageUIs.DECLINE_ALL)) {
            waitToElementClickable(RequestsPageUIs.DECLINE_ALL);
            clickToElement(RequestsPageUIs.DECLINE_ALL);
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
        String countRequestLabel = getTextElement(RequestsPageUIs.NUMBER_OF_MEMBER_DIDNOT_ANSWER_LABEL);
        String numberOfRequest = countRequestLabel.substring(0, 3);
        return numberOfRequest;
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

    public void listAndCheckIDRequested() {
        scrollToBottomPage();
        List<WebElement> allID = driver.findElements(By.xpath(RequestsPageUIs.LIST_ID_INPUTTED));
        for (WebElement id : allID) {
            scrollToElement(id);
            String requestID = id.getText();
            System.out.println(requestID);
            int idSize = requestID.length();
            if (!Character.isDigit(requestID.charAt(requestID.length() - 1))) {
                System.out.println("Decline ID: " + requestID + " - ID không đúng - Ký tự sau cùng");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElement(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            } else if (idSize > 8) {
                System.out.println("WARNING: ID " + requestID + " is > 8 characters");
            } else if (idSize > 12) {
                System.out.println("Decline ID: " + requestID + " due to > 12 characters");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElement(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            } else if(idSize < 6){
                System.out.println("Decline ID: " +requestID+" due to < 6 characters");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElement(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            } else if (requestID.startsWith("0")){
                System.out.println("Decline ID: " + requestID + "due to start with 0");
                waitToElementVisible(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
                clickToElement(RequestsPageUIs.DECLINE_BUTTON_FOR_EACH_ID, requestID);
            }
        }
        System.out.println("=================================================================");
    }

    public void pressEndKeyboard(){
        Actions action = new Actions(driver);
        action.keyDown(Keys.END).keyUp(Keys.END).perform();
    }

}
