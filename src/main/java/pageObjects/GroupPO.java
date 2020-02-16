package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.GroupPageUIs;

public class GroupPO extends AbstractPage{
	WebDriver driver;
	public GroupPO(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public void clickToMemberRequestLink() {
		waitToElementVisible(GroupPageUIs.REQUESTED_LIST_LINK);
		clickToElement(GroupPageUIs.REQUESTED_LIST_LINK);
	}

	public boolean isRequestPageAccessed(){
		String currentUrl = driver.getCurrentUrl();
		return currentUrl.contains("822546994752924/requests");
	}

}
