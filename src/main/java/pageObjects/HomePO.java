package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.HomePageUIs;

public class HomePO extends AbstractPage {
	WebDriver driver;
	public HomePO(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	
	public void inputToEmailTextbox(String email) {
		waitToElementVisible(HomePageUIs.EMAIL_TEXTBOX);
		sendKeyToElement(HomePageUIs.EMAIL_TEXTBOX, email);
	}
	
	public void inputToPasswordTextbox(String password) {
		waitToElementVisible(HomePageUIs.PASSWORD_TEXTBOX);
		sendKeyToElement(HomePageUIs.PASSWORD_TEXTBOX, password);
	}
	
	public void clickToLoginButton() {
		if (isElementPresentInDOM(HomePageUIs.LOGIN_BUTTON)) {
			waitToElementVisible(HomePageUIs.LOGIN_BUTTON);
			clickToElement(HomePageUIs.LOGIN_BUTTON);
		} else {
			waitToElementClickable(HomePageUIs.LOGIN_BUTTON2);
			clickToElement(HomePageUIs.LOGIN_BUTTON2);
		}
	}
	
	public void clickToGroupsLink() {
		waitToElementVisible(HomePageUIs.GROUP_LINK);
		clickToElement(HomePageUIs.GROUP_LINK);
	}
	
	public void clickToGroupNowDaNang() {
		waitToElementVisible(HomePageUIs.GROUP_NOW_DANANG_LINK);
		clickToElement(HomePageUIs.GROUP_NOW_DANANG_LINK);
	}

	public boolean isLoginSuccessfully(){
		waitToElementClickable(HomePageUIs.HOME_TITLE);
		return isElementDisplayed(HomePageUIs.HOME_TITLE);
	}

	public boolean isGroupAcessed(String groupID){
		String currentURL = driver.getCurrentUrl();
		return currentURL.contains(groupID);
	}
}
