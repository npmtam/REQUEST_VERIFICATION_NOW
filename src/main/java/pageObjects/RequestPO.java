package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.RequestsPageUIs;

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
        }
    }

    public void clickToConfirmButton(){
    	if (isElementPresentInDOM(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP)){
    		waitToElementClickable(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP);
    		clickToElement(RequestsPageUIs.CONFIRM_BUTTON_AT_CONFIRMATION_POPUP);
		}
	}

    public void clickToRemoveFilter() {
        waitToElementVisible(RequestsPageUIs.REMOVE_FILTER_LINK);
        clickToElement(RequestsPageUIs.REMOVE_FILTER_LINK);
    }

    public boolean isRequestEquals(int expectedResult){
		return countElements(RequestsPageUIs.REQUEST_PRESENT) == expectedResult;
	}


}
