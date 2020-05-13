package facebook.nowgroup;

import commons.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.GroupPO;
import pageObjects.HomePO;
import pageObjects.LoginPO;
import pageObjects.RequestPO;

public class Request_verification_now extends AbstractTest {
    private WebDriver driver;
    private AbstractPage abstractPage;
    private HomePO homePage;
    private GroupPO groupPage;
    private LoginPO loginPage;
    private ReadData readData;
    private RequestPO requestPage;

    String numberRequestNotAnswer;

    @Parameters("browser")
    @BeforeTest
    public void beforeTest(String browserName) {
        driver = getBrowserDriver(browserName);
        driver.get(Constants.URL);
        abstractPage = new AbstractPage(driver);
        readData = new ReadData(driver);

        log.info("Pre-condition: Login");
        homePage = PageGeneratorManager.getHomePage(driver);
        homePage.inputToEmailTextbox(Constants.EMAIL);
        homePage.inputToPasswordTextbox(Constants.PASSWORD);

        homePage.clickToLoginButton();
        abstractPage.sleepInSecond(1);

        log.info("Pre-condition: Verify login successfully");
        verifyTrue(homePage.isLoginSuccessfully());
    }

    @Test
    public void TC01_AccessGroupNow() {
        log.info("Step 01: Click to Group link");
        homePage.clickToGroupsLink();
        abstractPage.sleepInSecond(2);

        log.info("Step 02: Click to Now group link");
        homePage.clickToGroupNowDaNang();
        abstractPage.sleepInSecond(2);

        log.info("Step 03: Verify access Group successfully");
        verifyTrue(homePage.isGroupAcessed("822546994752924"));
    }

    @Test
    public void TC02_AccessRequestPage() {
        log.info("Step 04: Access request Page");
        groupPage = PageGeneratorManager.getGroupPage(driver);
        groupPage.clickToGroupManagementLink();
        groupPage.clickToRequestLink();

        groupPage.sleepInSecond(3);

        log.info("Step 05: Verify access request page successfully");
        verifyTrue(groupPage.isRequestPageAccessed());
    }

    @Test
    public void TC03_RemoveRequestDidNotAnswer() {
        log.info("Step 06: Select filter: Request didn't answer the question");
        requestPage = PageGeneratorManager.getRequestPage(driver);
        requestPage.clickToQuestionFilter();
        abstractPage.sleepInSecond(1);
        requestPage.clickToNoAnswerQuestionFilter();

        log.info("Step 07: Count the number of request not answer");
        numberRequestNotAnswer = requestPage.getNumberOfRequestNotAnswer();
        System.out.println("There are " + numberRequestNotAnswer + " requests that not answer the question");

        log.info("Step 08: Remove all request didn't answer");
        requestPage.clickToDeclineAllRequest();
        requestPage.clickToConfirmButton();

        log.info("Step 09: Verify declined didn't answer request");
        verifyTrue(requestPage.isRequestEquals(0));

        log.info("Step 10: Remove filter");
        requestPage.clickToRemoveFilter();

        System.out.println("Rejected " + numberRequestNotAnswer + " that not answer the question");
    }

    @Test
    public void TC04_SortByOldestFirst(){
        log.info("Step 11: Select filter by oldest first");
        requestPage.selectSortSubOptions(Constants.LATEST_SORT);

        log.info("Step 12: Verify the oldest was selected");
        requestPage.isOldestSortWasSelected();

        log.info("Step 13: Scroll to load full requests");
        requestPage.scrollToLoadID();

        log.info("Step 13: Check conditions and print ID requested");
        requestPage.listAndCheckIDRequested();

        log.info("Step 14: Write ID to CSV file");


    }

    @AfterTest(alwaysRun = true)
    public void afterClass() {
        closeBrowserAndDriver(driver);
    }

}
