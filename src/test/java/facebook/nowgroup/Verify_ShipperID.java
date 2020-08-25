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

public class Verify_ShipperID extends AbstractTest {
    private WebDriver driver;
    private AbstractPage abstractPage;
    private HomePO homePage;
    private GroupPO groupPage;
    private LoginPO loginPage;
    private ReadData readData;
    private RequestPO requestPage;

    String numberRequestNotAnswer, isThereCSVFile;
    String password = encryptData(Constants.PASSWORD);
    String getPasswordDecrypted = getDecryptedText(password);

    @Parameters({"browser", "isThereCSVFile"})
    @BeforeTest
    public void beforeTest(String browserName, String isThereCSVFile) {
        driver = getBrowserDriver(browserName);
        driver.get(Constants.URL);
        abstractPage = new AbstractPage(driver);
        readData = new ReadData();
        isThereCSVFile = this.isThereCSVFile;

        log.info("Pre-condition: Login");
        homePage = PageGeneratorManager.getHomePage(driver);
        homePage.inputToEmailTextbox(Constants.EMAIL);
        homePage.inputToPasswordTextbox(getPasswordDecrypted);

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
//        groupPage.clickToGroupManagementLink();
        groupPage.clickToMemberRequestsLink();

        log.info("Step 05: Verify access request page successfully");
        verifyTrue(groupPage.isRequestPageAccessed());
    }

    @Test
    public void TC03_RemoveRequestDidNotAnswer() {
        log.info("Step 06: Select filter: Request didn't answer the question");
        requestPage = PageGeneratorManager.getRequestPage(driver);
        requestPage.clickToQuestionFilter();
        requestPage.clickToNoAnswerQuestionFilter();

        log.info("Step 07: Count the number of request not answer");
        numberRequestNotAnswer = requestPage.getNumberOfRequestNotAnswer();
        System.out.println("There are " + numberRequestNotAnswer + " requests that not answer the question");

        requestPage.sleepInSecond(2);

        log.info("Step 08: Remove all request didn't answer");
        requestPage.clickToDeclineAllRequest();
        requestPage.clickToConfirmButton();

        log.info("Step 09: Verify no requests without answered remaining");
        verifyTrue(requestPage.isThereTheResult());

        log.info("Step 10: Remove filter");
        requestPage.clickToRemoveFilter();

        System.out.println("Rejected " + numberRequestNotAnswer + " request(s) that not answer the question");
    }

//    @Test
    public void TC04_SortAndReadData() {
        log.info("Step 11: Select filter by oldest first");
        requestPage.selectSortSubOptions(Constants.LATEST_SORT);

        log.info("Step 12: Verify the oldest was selected");
        requestPage.isOldestSortWasSelected();

        log.info("Step 13: Scroll to load full requests");
        requestPage.scrollToLoadID();

        log.info("Step 13: Read data and verify depends on settings");
        if (isThereCSVFile.equals("true")) {
            requestPage.readAndHandleRequestID();
        } else {
            System.out.println("Did not read data and handle requests");
        }

        log.info("Step 14: Check conditions and print ID requested");
        requestPage.listAndCheckIDRequested();
    }

    @AfterTest(alwaysRun = true)
    public void afterClass() {
        closeBrowserAndDriver(driver);
    }

}
