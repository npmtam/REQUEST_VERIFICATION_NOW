package commons;

import org.openqa.selenium.WebDriver;
import pageObjects.*;

public class PageGeneratorManager {

	public static HomePO getHomePage(WebDriver driver){
		return new HomePO(driver);
	}

	public static LoginPO getLoginPage (WebDriver driver){
		return new LoginPO(driver);
	}

	public static RequestPO getRequestPage(WebDriver driver){
		return new RequestPO(driver);
	}

	public static GroupPO getGroupPage(WebDriver driver){
		return new GroupPO(driver);
	}

}
