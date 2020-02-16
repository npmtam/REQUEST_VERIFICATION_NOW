package pageObjects;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;

public class LoginPO extends AbstractPage {
    WebDriver driver;

    public LoginPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
}
